package main

import (
	"fmt"
	"math/rand"
	"os"
	"strconv"
	"strings"
	"sync"
	"time"
)

// 0 - withered
// 1 - watered

func streamGardener(garden [][]int, mutex *sync.RWMutex) {
	for {
		mutex.RLock()
		for i := 0; i < len(garden); i++ {
			for j := 0; j < len(garden[i]); j++ {
				if garden[i][j] == 0 {
					garden[i][j] = 1
				}
			}
		}
		mutex.RUnlock()
		time.Sleep(7000 * time.Millisecond)
	}
}

func streamNature(garden [][]int, mutex *sync.RWMutex) {
	for {
		mutex.Lock()
		garden[rand.Intn(len(garden)-1)][rand.Intn(len(garden[0])-1)] = 1
		garden[rand.Intn(len(garden)-1)][rand.Intn(len(garden[0])-1)] = 0
		mutex.Unlock()
		time.Sleep(1000 * time.Millisecond)
	}
}

func streamMonitor1(garden [][]int, mutex *sync.RWMutex) {
	file, err := os.Create("garden.txt")

	if err != nil {
		fmt.Println("Can't create file ", err)
		os.Exit(1)
	}
	defer file.Close()

	for {
		mutex.RLock()
		for i := 0; i < len(garden); i++ {
			var stringSlice []string
			for _, value := range garden[i] {
				stringSlice = append(stringSlice, strconv.Itoa(value))
			}
			line := strings.Join(stringSlice, "")
			file.WriteString(line + "\n")
		}
		mutex.RUnlock()
		file.WriteString("\n")
		time.Sleep(2000 * time.Millisecond)
	}
}

func streamMonitor2(garden [][]int, mutex *sync.RWMutex) {
	for {
		mutex.RLock()
		for _, element := range garden {
			for _, element1 := range element {
				print(element1)
			}
			println()
		}
		mutex.RUnlock()
		println()
		time.Sleep(2000 * time.Millisecond)
	}
}

func main() {
	var garden [][]int
	var waitGroup sync.WaitGroup
	var rwMutex sync.RWMutex

	for j := 0; j < 10; j++ {
		var row []int
		for i := 0; i < 10; i++ {
			row = append(row, 1)
		}
		garden = append(garden, row)
	}
	waitGroup.Add(4)
	go streamMonitor1(garden, &rwMutex)
	go streamMonitor2(garden, &rwMutex)
	go streamNature(garden, &rwMutex)
	go streamGardener(garden, &rwMutex)
	waitGroup.Wait()
}
