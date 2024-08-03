
SRC_DIR="."
JAVA_DST_DIR="../sneknet_android/app/src/main/java/"
GOLANG_DST_DIR="../sneknet_host/"

protoc -I=$SRC_DIR \
--go_out=$GOLANG_DST_DIR \
--java_out=$JAVA_DST_DIR \
$SRC_DIR/ipc.proto
