package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Customer struct {
	id          int
	currentCash *Cash
	cashiers    []*Cash
}

type Cash struct {
	id            int
	customerQueue chan *Customer
}

func (c *Cash) enterQueue(customer *Customer) {
	c.customerQueue <- customer
}

func (c *Cash) leaveQueue(customer *Customer) {
	for i := range c.customerQueue {
		if i == customer {
			close(c.customerQueue)
			break
		}
	}
}

func (c *Cash) serveCustomer(wg *sync.WaitGroup) {
	defer wg.Done()
	for customer := range c.customerQueue {
		fmt.Printf("Cash %d serving customer %d\n", c.id, customer.id)
		c.leaveQueue(customer)
	}
}

func (c *Customer) getQueueWithFewerPeopleThanOrdinal() int {
	minCustomers := len(c.cashiers) + 1
	selectedQueue := -1

	for i, cashier := range c.cashiers {
		customersInQueue := len(cashier.customerQueue)
		if customersInQueue < i {
			if customersInQueue < minCustomers {
				minCustomers = customersInQueue
				selectedQueue = i
			}
		}
	}

	return selectedQueue
}

func (c *Customer) run(wg *sync.WaitGroup) {
	defer wg.Done()
	for {
		newCash := c.getQueueWithFewerPeopleThanOrdinal()
		if newCash != c.currentCash.id && newCash != -1 {
			c.currentCash.leaveQueue(c)
			fmt.Printf("Customer %d entered queue at cash %d\n", c.id, newCash)
			targetCash := c.cashiers[newCash]
			targetCash.enterQueue(c)
		}
	}
}

func main() {
	numberOfCashiers := 3
	numberOfCustomers := 10
	rand.Seed(time.Now().UnixNano())

	cashiers := make([]*Cash, numberOfCashiers)
	for i := 0; i < numberOfCashiers; i++ {
		cashiers[i] = &Cash{
			id:            i,
			customerQueue: make(chan *Customer),
		}
	}

	var wg sync.WaitGroup

	for i := 0; i < numberOfCustomers; i++ {
		customer := &Customer{
			id:          i,
			currentCash: cashiers[rand.Intn(numberOfCashiers)],
			cashiers:    cashiers,
		}
		wg.Add(1)
		go customer.run(&wg)
	}

	for _, cashier := range cashiers {
		wg.Add(1)
		go cashier.serveCustomer(&wg)
	}

	wg.Wait()
}
