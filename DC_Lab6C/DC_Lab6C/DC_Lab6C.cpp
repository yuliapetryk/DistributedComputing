#include <iostream>
#include <mpi.h>
#include "Multiplication.h"


int main(int argc, char* argv[])
{
	srand(time(0));
	setvbuf(stdout, 0, _IONBF, 0);
	MPI_Init(&argc, &argv);

	MPI_Comm_rank(MPI_COMM_WORLD, &ProcRank);
	MPI_Comm_size(MPI_COMM_WORLD, &ProcNum);
	switch (ProcNum) {
	case 1:
		
		Multiplication::runTest(argc, argv, 150);
		Multiplication::runTest(argc, argv, 300);
		Multiplication::runTest(argc, argv, 600);
		Multiplication::runTest(argc, argv, 1200);
		break;

	case 4:
		
		Multiplication::runTest(argc, argv, 144);
		Multiplication::runTest(argc, argv, 288);
		Multiplication::runTest(argc, argv, 576);
		Multiplication::runTest(argc, argv, 1152);
		break;
	
	case 9:
		Multiplication::runTest(argc, argv, 162);
		Multiplication::runTest(argc, argv, 324);
		Multiplication::runTest(argc, argv, 648);
		Multiplication::runTest(argc, argv, 1296);
		break;

	default:
		std::cout << "\n Invalid number of processes!";
	}
		
	MPI_Finalize();
	return 0;
}
