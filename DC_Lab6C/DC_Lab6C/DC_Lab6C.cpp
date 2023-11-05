#include <iostream>
#include <mpi.h>
#include "Multiplication.h"


int main(int argc, char* argv[])
{
	srand(time(0));
	setvbuf(stdout, 0, _IONBF, 0);
	MPI_Init(&argc, &argv);

	for (int i = 1; i <= 3; i++) {

		ProcRank = i*i;
		std::cout << std::endl;
		std::cout << "ProcRank = "<< ProcRank << std::endl;

		MPI_Comm_rank(MPI_COMM_WORLD, &ProcRank);
		MPI_Comm_size(MPI_COMM_WORLD, &ProcNum);

		Multiplication::runTest(argc, argv, 500);
		Multiplication::runTest(argc, argv, 1000);
		Multiplication::runTest(argc, argv, 1500);
		Multiplication::runTest(argc, argv, 2000);
		Multiplication::runTest(argc, argv, 2500);
		Multiplication::runTest(argc, argv, 3000);
		
	}
	MPI_Finalize();
	return 0;
}
