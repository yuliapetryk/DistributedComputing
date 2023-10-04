package main

import (
	"math"
)

func duel(currentRound chan int, nextRound chan int) {
	monk1 := <-currentRound
	monk2 := <-currentRound
	if monk1 > monk2 {
		println("The monk with energy", monk1, "beat the monk with energy", monk2)
		nextRound <- monk1
	} else {
		println("The monk with energy", monk2, "beat the monk with energy", monk1)
		nextRound <- monk2
	}
}

func main() {
	const size = 8
	energy := make(chan int, size)
	en := []int{100, 90, 110, 80, 120, 70, 60, 135}
	for i := 0; i < size; i++ {
		energy <- en[i]
	}

	rounds := int(math.Log2(size))
	duels := size / 2

	for round := 1; round <= rounds; round++ {
		nextRound := make(chan int, duels)
		if round == rounds {
			go duel(energy, nextRound)
			winner := <-nextRound
			println("The monk with energy", winner, "won!")
			break
		}

		for duelIndex := 0; duelIndex < duels; duelIndex++ {
			go duel(energy, nextRound)
		}

		duels /= 2
		energy = nextRound
	}
}
