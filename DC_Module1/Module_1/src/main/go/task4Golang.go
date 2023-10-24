package main

import (
	"fmt"
	"sync"
	"time"
)

type BusStop struct {
	availableSeats chan struct{}
}

func NewBusStop(limit int) *BusStop {
	return &BusStop{
		availableSeats: make(chan struct{}, limit),
	}
}

func (bs *BusStop) tryToStop(name string, number int) bool {
	select {
	case bs.availableSeats <- struct{}{}:
		fmt.Printf("%s arrived at stop %d\n", name, number)
		return true
	default:
		fmt.Printf("%s couldn't stop at stop %d, moving to the next stop\n", name, number)
		return false
	}
}

func (bs *BusStop) leaveBusStop() {
	<-bs.availableSeats
}

type Bus struct {
	name  string
	stops []*BusStop
}

func NewBus(name string, stops []*BusStop) *Bus {
	return &Bus{
		name:  name,
		stops: stops,
	}
}

func (b *Bus) run(wg *sync.WaitGroup) {
	defer wg.Done()
	for i, stop := range b.stops {
		if stop.tryToStop(b.name, i) {
			time.Sleep(100 * time.Millisecond)
			fmt.Printf("%s left the stop %d\n", b.name, i)
			stop.leaveBusStop()
		}
	}
}

func main() {
	numberOfStops := 5
	limit := 3

	stops := make([]*BusStop, numberOfStops)
	for i := 0; i < numberOfStops; i++ {
		stops[i] = NewBusStop(limit)
	}

	var wg sync.WaitGroup

	wg.Add(4)

	busNames := []string{"Bus 1", "Bus 2", "Bus 3", "Bus 4"}

	for _, name := range busNames {
		go NewBus(name, stops).run(&wg)
	}

	wg.Wait()
}
