package common

import (
	"errors"
	"fmt"
	"os/exec"
)

type ADB struct {
	AdbPath string
}

func (a *ADB) Forward(androidPort int, hostPort int) error {
	//using TCP for now, UDP works via hacks only
	androidPortStr := fmt.Sprintf("tcp:%d", androidPort)
	hostPortStr := fmt.Sprintf("tcp:%d", hostPort)

	c := exec.Command(a.AdbPath,
		"forward",
		androidPortStr,
		hostPortStr,
	)
	o, err := c.Output()
	
	
	if err != nil {
		return errors.Join(
			fmt.Errorf(
				"error running %s, output: %s, error: %s",
				c.String(),
				string(o),
				err.(*exec.ExitError).Stderr,
			),
			err,
		)
		// fmt.Println(string(o))
		// return err
	}
	return nil
}

func (a *ADB) StopForward(hostPort int) error {
	hostPortStr := fmt.Sprintf("udp:%d", hostPort)

	c := exec.Command("adb",
		"foward",
		"--remove",
		hostPortStr,
	)
	o, err := c.Output()
	if err != nil {
		fmt.Println(string(o))
		return err
	}
	return nil
}
