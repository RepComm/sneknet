package common

import (
	"os"
	"os/signal"
	"syscall"
)

func HandleSigTerm(cb func(sig os.Signal)) {
	signalChannel := make(chan os.Signal, 2)
	signal.Notify(signalChannel, os.Interrupt, syscall.SIGTERM)
	go func() {
		sig := <-signalChannel
		switch sig {
		case os.Interrupt:
			cb(sig)
		case syscall.SIGTERM:
			cb(sig)
		}
	}()
}
