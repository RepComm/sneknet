package common

import (
	"bufio"
	"os"
	"strings"
)

type ArgsResult = map[string]string

func ArgsParse(data string) (string, ArgsResult) {
	result := make(ArgsResult)

	parts := strings.Split(data, " -")

	start := ""

	for i := 0; i < len(parts); i++ {
		part := parts[i]
		if i == 0 {
			start = part
			continue
		}

		strs := strings.Split(part, "=")
		k := strs[0]
		v := "true"
		if len(strs) > 1 {
			v = strs[1]
		}
		result[k] = v

	}

	return start, result
}

type StdLineCmd struct {
	Cmd  string
	Args ArgsResult
}

type StdLineCmds struct {
	endChannel chan bool
	cb         func(cmd StdLineCmd)
}

// handles actual reading/parsing, expects to be run in a goroutine
func (c *StdLineCmds) handleLineReads() {
	scanner := bufio.NewScanner(os.Stdin)
	for {
		select {
		case <-c.endChannel:
			close(c.endChannel)
			return
		default:
		}

		if !scanner.Scan() {
			continue
		}

		//otherwise we keep scanning
		line := scanner.Text()
		//and parsing
		cmd, args := ArgsParse(line)

		result := StdLineCmd{
			Cmd:  cmd,
			Args: args,
		}

		//and sending the results back to the main thread
		c.cb(result)
	}
}

// start line reading go routine
func (c *StdLineCmds) Start(cb func(cmd StdLineCmd)) bool {
	c.cb = cb
	if c.endChannel != nil {
		return false
	}

	c.endChannel = make(chan bool, 1)
	go c.handleLineReads()
	return true
}

// stop line reading go routine
func (c *StdLineCmds) Stop() {
	c.endChannel <- true
}
