#pragma once
#include <iostream>
#include <random>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <mpi.h>
#include <iostream>

static int ProcNum = 0;
static int ProcRank = 0;
static MPI_Comm ColComm;
static MPI_Comm RowComm;

namespace Multiplication {
	
	namespace General {
		double* RandomDataInitialization(  int Size) {
			int MatrSize = Size * Size;
			double* Matr = new double[MatrSize];
			for (int i = 0; i < MatrSize; i++) {
				Matr[i] = (rand() % (5 ) );
			}
			return Matr;
		}


		void simpleMultiplication(double* pAMatrix, double*& pBMatrix, double*& pCMatrix, int Size) {
			for (int i = 0; i < Size; i++) {
				for (int j = 0; j < Size; j++) {
					for (int k = 0; k < Size; k++) {
						pCMatrix[i * Size + j] += pAMatrix[i * Size + k] * pBMatrix[k * Size + j];
					}
				}
			}
		}
		
		
		

	}

	namespace Fox {
		int GridCoords[2];
		int GridSize;
		MPI_Comm GridComm;
	    MPI_Comm ColComm;
		MPI_Comm RowComm;

		void Deconstruct(double*& pAMatrix, double*& pBMatrix, double*& pCMatrix, double* pAblock, double* pBblock, double* pCblock, double* pTemporaryAblock = NULL) {
			if (ProcRank == 0) {
				delete[] pAMatrix;
				delete[] pBMatrix;
				delete[] pCMatrix;
			}
			delete[] pAblock;
			delete[] pBblock;
			delete[] pCblock;
			if (!pTemporaryAblock) {
				delete[] pTemporaryAblock;
			}
		}

		void ResultCollection(double* pCMatrix, double* pCblock, int Size, int BlockSize, int GridCoords[]) {
			double* res_row = new double[Size * BlockSize];
			for (int i = 0; i < BlockSize; i++) {
				MPI_Gather(&pCblock[i * BlockSize], BlockSize, MPI_DOUBLE, &res_row[i * Size], BlockSize, MPI_DOUBLE, 0, RowComm);
			}
			if (GridCoords[1] == 0) {
				MPI_Gather(res_row, BlockSize * Size, MPI_DOUBLE, pCMatrix, BlockSize * Size, MPI_DOUBLE, 0, ColComm);
			}
			delete[] res_row;
		}
		
		void createGridCommunicators() {
			int DimSize[2];
			int Periodic[2];
			int Subdims[2];

			DimSize[0] = GridSize;
			DimSize[1] = GridSize;
			Periodic[0] = 0;
			Periodic[1] = 0;

			MPI_Cart_create(MPI_COMM_WORLD, 2, DimSize, Periodic, 1, &GridComm);
			MPI_Cart_coords(GridComm, ProcRank, 2, GridCoords);

			Subdims[0] = 0;
			Subdims[1] = 1;
			MPI_Cart_sub(GridComm, Subdims, &RowComm);

			Subdims[0] = 1;
			Subdims[1] = 0;
			MPI_Cart_sub(GridComm, Subdims, &ColComm);
		}

		void DataDistributionMatrices(double* matrix, double* matrixBlock, int Size, int BlockSize) {
			double* Row = new double[BlockSize * Size];
			if (GridCoords[1] == 0) {
				MPI_Scatter(matrix, BlockSize * Size, MPI_DOUBLE, Row, BlockSize * Size, MPI_DOUBLE, 0, ColComm);
			}
			for (int i = 0; i < BlockSize; i++) {
				MPI_Scatter(&Row[i * Size], BlockSize, MPI_DOUBLE, &(matrixBlock[i * BlockSize]), BlockSize, MPI_DOUBLE, 0, RowComm);
			}
			delete[] Row;
		}

		void DataDistribution(double* pAMatrix, double* pBMatrix, double* pAblock, double* pBblock, int Size, int BlockSize) {
			DataDistributionMatrices(pAMatrix, pAblock, Size, BlockSize);
			DataDistributionMatrices(pBMatrix, pBblock, Size, BlockSize);
		}
		
		void ABlockCommunication(int iter, double* pAblock, double* pMatrixAblock, int BlockSize) {
			int Pivot = (GridCoords[0] + iter) % GridSize;
			if (GridCoords[1] == Pivot) {
				for (int i = 0; i < BlockSize * BlockSize; i++)
					pAblock[i] = pMatrixAblock[i];
			}
			MPI_Bcast(pAblock, BlockSize * BlockSize, MPI_DOUBLE, Pivot, RowComm);
		}

		void BblockCommunication(double* pBblock, int BlockSize) {
			MPI_Status Status;
			int NextProc = GridCoords[0] + 1;
			if (GridCoords[0] == GridSize - 1) NextProc = 0;
			int  PrevProc = GridCoords[0] - 1;
			if (GridCoords[0] == 0)  PrevProc = GridSize - 1;
			MPI_Sendrecv_replace(pBblock, BlockSize * BlockSize, MPI_DOUBLE, NextProc, 0, PrevProc, 0, ColComm, &Status);
		}

		void ParallelResultCalculation (double* pAblock, double* pMatrixAblock,
			double* pBblock, double* pCblock, int BlockSize) {
			for (int i = 0; i < GridSize; i++) {
				ABlockCommunication(i, pAblock, pMatrixAblock, BlockSize);
				General::simpleMultiplication(pAblock, pBblock, pCblock, BlockSize);
				BblockCommunication(pBblock, BlockSize);
			}
		}

		void ProcessInitialization(double*& pAMatrix, double*& pBMatrix, double*& pCMatrix, double*& pAblock, double*& pBblock, double*& pCblock, double*& pTemporaryAblock, int& Size, int& BlockSize) {
			BlockSize = Size / GridSize;

			pAblock = new double[BlockSize * BlockSize];
			pBblock = new double[BlockSize * BlockSize];
			pCblock = new double[BlockSize * BlockSize];
			pTemporaryAblock = new double[BlockSize * BlockSize];

			if (ProcRank == 0) {
				pAMatrix = new double[Size * Size];
				pBMatrix = new double[Size * Size];
				pCMatrix = new double[Size * Size];
				pAMatrix = General::RandomDataInitialization(Size);
				pBMatrix = General::RandomDataInitialization(Size);
			}
		}

		void runFoxAlg(int argc, char* argv[], int dim) {
			double* pAMatrix; 
			double* pBMatrix; 
			double* pCMatrix; 
			int Size; 
			int BlockSize;
			double* pAblock; 
			double* pBblock; 
			double* pCblock; 
			double* pMatrixAblock;
			double Start, Finish, Duration;

			GridSize = sqrt((double)ProcNum);
			if (ProcNum != GridSize * GridSize) {
				if (ProcRank == 0) {
					std::cout << "\nNumber of processes must be a perfect square \n";
				}
				return;
			}
			Size = dim;
			createGridCommunicators();
			ProcessInitialization( pAMatrix, pBMatrix, pCMatrix, pAblock, pBblock,
				pCblock, pMatrixAblock, Size, BlockSize);
			DataDistribution(pAMatrix, pBMatrix, pMatrixAblock, pBblock, Size,
				BlockSize);

			Start = MPI_Wtime();
			ParallelResultCalculation(pAblock, pMatrixAblock, pBblock,
				pCblock, BlockSize);
			Finish = MPI_Wtime();
			ResultCollection(pCMatrix, pCblock, Size, BlockSize, GridCoords);
			Deconstruct(pAMatrix, pBMatrix, pCMatrix, pAblock, pBblock, pCblock, pAblock);

			Duration = Finish - Start;
			if (ProcRank == 0)
				std::cout << "Fox (Size " << Size << "x" << Size << " ): " << Duration << std::endl;
		}
	}

	namespace Cannon {
		int GridCoords[2];
		int GridSize;
		MPI_Comm GridComm;
		MPI_Comm ColComm;
		MPI_Comm RowComm;

		void Deconstruct(double*& pAMatrix, double*& pBMatrix, double*& pCMatrix, double* pAblock, double* pBblock, double* pCblock, double* pTemporaryAblock = NULL) {
			if (ProcRank == 0) {
				delete[] pAMatrix;
				delete[] pBMatrix;
				delete[] pCMatrix;
			}
			delete[] pAblock;
			delete[] pBblock;
			delete[] pCblock;
			if (!pTemporaryAblock) {
				delete[] pTemporaryAblock;
			}
		}

		void ProcessInitialization( double*& pAMatrix, double*& pBMatrix, double*& pCMatrix, double*& pAblock, double*& pBblock, double*& pCblock,  int& Size, int& BlockSize) {
			BlockSize = Size / GridSize;

			pAblock = new double[BlockSize * BlockSize];
			pBblock = new double[BlockSize * BlockSize];
			pCblock = new double[BlockSize * BlockSize];
			

			if (ProcRank == 0) {
				pAMatrix = new double[Size * Size];
				pBMatrix = new double[Size * Size];
				pCMatrix = new double[Size * Size];
				pAMatrix = General::RandomDataInitialization(Size);
				pBMatrix = General::RandomDataInitialization(Size);
			}
		}

		void ResultCollection(double* pCMatrix, double* pCblock, int Size, int BlockSize, int GridCoords[]) {
			double* res_row = new double[Size * BlockSize];
			for (int i = 0; i < BlockSize; i++) {
				MPI_Gather(&pCblock[i * BlockSize], BlockSize, MPI_DOUBLE, &res_row[i * Size], BlockSize, MPI_DOUBLE, 0, RowComm);
			}
			if (GridCoords[1] == 0) {
				MPI_Gather(res_row, BlockSize * Size, MPI_DOUBLE, pCMatrix, BlockSize * Size, MPI_DOUBLE, 0, ColComm);
			}
			delete[] res_row;
		}

		void createGridCommunicators() {
			int DimSize[2];
			int Periodic[2];
			int Subdims[2];

			DimSize[0] = GridSize;
			DimSize[1] = GridSize;
			Periodic[0] = 0;
			Periodic[1] = 0;

			MPI_Cart_create(MPI_COMM_WORLD, 2, DimSize, Periodic, 1, &GridComm);
			MPI_Cart_coords(GridComm, ProcRank, 2, GridCoords);

			Subdims[0] = 0;
			Subdims[1] = 1;
			MPI_Cart_sub(GridComm, Subdims, &RowComm);

			Subdims[0] = 1;
			Subdims[1] = 0;
			MPI_Cart_sub(GridComm, Subdims, &ColComm);
		}

		void ABlockCommunication(double* pAMatrix, int Size, int BlockSize) {
			int NextCoord = GridCoords[1] + 1;
			if (GridCoords[1] == GridSize - 1) NextCoord = 0;
			int PrevCoord = GridCoords[1] - 1;
			if (GridCoords[1] == 0) PrevCoord = GridSize - 1;
			MPI_Status Status;
			MPI_Sendrecv_replace(pAMatrix, BlockSize * BlockSize, MPI_DOUBLE, NextCoord, 0, PrevCoord, 0, RowComm, &Status);
		}
		
		void BBlockCommunication(double* pBMatrix, int Size, int BlockSize) {
			MPI_Status Status;
			int NextProc = GridCoords[0] + 1;
			if (GridCoords[0] == GridSize - 1) NextProc = 0;
			int PrevProc = GridCoords[0] - 1;
			if (GridCoords[0] == 0) PrevProc = GridSize - 1;
			MPI_Sendrecv_replace(pBMatrix, BlockSize * BlockSize, MPI_DOUBLE, NextProc, 0, PrevProc, 0, ColComm, &Status);
		}

		void ParallelResultCalculation(double* pAMatrix, double* pBMatrix, double* pCMatrix, int Size, int BlockSize) {
			for (int i = 0; i < GridSize; ++i) {
				General::simpleMultiplication(pAMatrix, pBMatrix, pCMatrix, BlockSize);
				ABlockCommunication(pAMatrix, Size, BlockSize);
				BBlockCommunication(pBMatrix, Size, BlockSize);
			}
		}

		void  DataDistributionBlock(double* Matrix, double* Block, int Row, int Col, int Size, int BlockSize) {
			int Position = Col * BlockSize * Size + Row * BlockSize;
			for (int i = 0; i < BlockSize; ++i, Position += Size) {
				MPI_Scatter(&Matrix[Position], BlockSize, MPI_DOUBLE, &(Block[i * BlockSize]), BlockSize, MPI_DOUBLE, 0, GridComm);
			}
		}

		void  DataDistribution(double* pAMatrix, double* pBMatrix, double* pAblock, double* pBblock, int Size, int BlockSize) {
			DataDistributionBlock(pAMatrix, pAblock, GridCoords[0], (GridCoords[0] + GridCoords[1]) % GridSize, Size, BlockSize);
			DataDistributionBlock(pBMatrix, pBblock, (GridCoords[0] + GridCoords[1]) % GridSize, GridCoords[1], Size, BlockSize);
		}

		void runCannonAlg(int argc, char* argv[], int dim) {
			double* pAMatrix;
			double* pBMatrix;
			double* pCMatrix;
			int Size;
			int BlockSize;
			double* pAblock;
			double* pBblock;
			double* pCblock;
			double* pMatrixAblock;
			double Start, Finish, Duration;

			GridSize = sqrt((double)ProcNum);
			if (ProcNum != GridSize * GridSize) {
				if (ProcRank == 0) {
					std::cout << "\nNumber of processes must be a perfect square \n";
				}
				return;
			}
			Size = dim;
			createGridCommunicators();
			ProcessInitialization( pAMatrix, pBMatrix, pCMatrix, pAblock, pBblock,
				pCblock, Size, BlockSize);
			DataDistribution(pAMatrix, pBMatrix, pAblock, pBblock, Size,
				BlockSize);

			Start = MPI_Wtime();
			ParallelResultCalculation(pAblock, pBblock, pCblock, Size, BlockSize);
			Finish = MPI_Wtime();
			ResultCollection(pCMatrix, pCblock, Size, BlockSize, GridCoords);
			Deconstruct(pAMatrix, pBMatrix, pCMatrix, pAblock, pBblock, pCblock, pAblock);

			Duration = Finish - Start;
			if (ProcRank == 0)
				std::cout << "Cannon (Size " << Size << "x" << Size << " ): " << Duration << std::endl;
		}
	}

	void runTest(int argc, char* argv[], int dim) {
	
		Fox::runFoxAlg(argc, argv, dim);
		Cannon::runCannonAlg(argc, argv, dim);
	}
};

