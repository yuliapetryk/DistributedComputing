package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var semaphoreMediator = make(chan int)
var semaphoresSmokers = [3]chan int{make(chan int), make(chan int), make(chan int)}
var components = [3]string{"tobacco", "paper", "matches"}
var wg sync.WaitGroup

func main() {
	wg.Add(6)
	go mediator()
	for i := 0; i < 3; i++ {
		go smoker(i)
	}
	wg.Wait()
}

type Components struct {
	component1 int
	component2 int
	component3 int
}

func giveComponents() Components {
	var components [3]int
	for i := 0; i < 3; i++ {
		components[i] = i
	}
	for i := range components {
		j := rand.Intn(i + 1)
		components[i], components[j] = components[j], components[i]
	}
	return Components{
		component1: components[0],
		component2: components[1],
		component3: components[2],
	}
}

func mediator() {
	for {
		componentsOnTable := giveComponents()
		println("The mediator puts on the table " + components[componentsOnTable.component1] + " and " + components[componentsOnTable.component2])
		semaphoresSmokers[componentsOnTable.component3] <- 0
		<-semaphoreMediator

	}
}

func smoker(component int) {
	for {
		<-semaphoresSmokers[component]
		fmt.Println("Smoker has " + components[component] + ", so he can smoke a cigarette")
		fmt.Println()
		time.Sleep(3 * time.Second)
		semaphoreMediator <- 0
	}
}
