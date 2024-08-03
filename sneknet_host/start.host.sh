#set the adb path env variable that sneknet looks for
export ADB_PATH=$(which adb)

#run sneknet with elevated privs for tunnel creation
sudo -E ~/go/bin/go run ./host/main.go