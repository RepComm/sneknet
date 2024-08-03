// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ipc.proto

// Protobuf Java Version: 4.26.0
package com.repcomm.sneknet.protos;

/**
 * Protobuf type {@code ipc.IPv4TcpMsg}
 */
public final class IPv4TcpMsg extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:ipc.IPv4TcpMsg)
    IPv4TcpMsgOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 0,
      /* suffix= */ "",
      IPv4TcpMsg.class.getName());
  }
  // Use IPv4TcpMsg.newBuilder() to construct.
  private IPv4TcpMsg(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private IPv4TcpMsg() {
    payload_ = com.google.protobuf.ByteString.EMPTY;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.repcomm.sneknet.protos.IPC.internal_static_ipc_IPv4TcpMsg_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.repcomm.sneknet.protos.IPC.internal_static_ipc_IPv4TcpMsg_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.repcomm.sneknet.protos.IPv4TcpMsg.class, com.repcomm.sneknet.protos.IPv4TcpMsg.Builder.class);
  }

  private int bitField0_;
  public static final int SRCPORT_FIELD_NUMBER = 1;
  private int srcPort_ = 0;
  /**
   * <code>int32 SrcPort = 1;</code>
   * @return The srcPort.
   */
  @java.lang.Override
  public int getSrcPort() {
    return srcPort_;
  }

  public static final int DSTPORT_FIELD_NUMBER = 2;
  private int dstPort_ = 0;
  /**
   * <code>int32 DstPort = 2;</code>
   * @return The dstPort.
   */
  @java.lang.Override
  public int getDstPort() {
    return dstPort_;
  }

  public static final int IPV4_FIELD_NUMBER = 3;
  private com.repcomm.sneknet.protos.IPv4Info iPv4_;
  /**
   * <code>.ipc.IPv4Info IPv4 = 3;</code>
   * @return Whether the iPv4 field is set.
   */
  @java.lang.Override
  public boolean hasIPv4() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>.ipc.IPv4Info IPv4 = 3;</code>
   * @return The iPv4.
   */
  @java.lang.Override
  public com.repcomm.sneknet.protos.IPv4Info getIPv4() {
    return iPv4_ == null ? com.repcomm.sneknet.protos.IPv4Info.getDefaultInstance() : iPv4_;
  }
  /**
   * <code>.ipc.IPv4Info IPv4 = 3;</code>
   */
  @java.lang.Override
  public com.repcomm.sneknet.protos.IPv4InfoOrBuilder getIPv4OrBuilder() {
    return iPv4_ == null ? com.repcomm.sneknet.protos.IPv4Info.getDefaultInstance() : iPv4_;
  }

  public static final int ISSYN_FIELD_NUMBER = 4;
  private boolean isSyn_ = false;
  /**
   * <code>bool IsSyn = 4;</code>
   * @return The isSyn.
   */
  @java.lang.Override
  public boolean getIsSyn() {
    return isSyn_;
  }

  public static final int ISACK_FIELD_NUMBER = 5;
  private boolean isAck_ = false;
  /**
   * <code>bool IsAck = 5;</code>
   * @return The isAck.
   */
  @java.lang.Override
  public boolean getIsAck() {
    return isAck_;
  }

  public static final int SEQID_FIELD_NUMBER = 6;
  private int seqId_ = 0;
  /**
   * <code>uint32 SeqId = 6;</code>
   * @return The seqId.
   */
  @java.lang.Override
  public int getSeqId() {
    return seqId_;
  }

  public static final int PAYLOAD_FIELD_NUMBER = 7;
  private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>bytes Payload = 7;</code>
   * @return The payload.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getPayload() {
    return payload_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (srcPort_ != 0) {
      output.writeInt32(1, srcPort_);
    }
    if (dstPort_ != 0) {
      output.writeInt32(2, dstPort_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(3, getIPv4());
    }
    if (isSyn_ != false) {
      output.writeBool(4, isSyn_);
    }
    if (isAck_ != false) {
      output.writeBool(5, isAck_);
    }
    if (seqId_ != 0) {
      output.writeUInt32(6, seqId_);
    }
    if (!payload_.isEmpty()) {
      output.writeBytes(7, payload_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (srcPort_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, srcPort_);
    }
    if (dstPort_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, dstPort_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getIPv4());
    }
    if (isSyn_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(4, isSyn_);
    }
    if (isAck_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(5, isAck_);
    }
    if (seqId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(6, seqId_);
    }
    if (!payload_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(7, payload_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.repcomm.sneknet.protos.IPv4TcpMsg)) {
      return super.equals(obj);
    }
    com.repcomm.sneknet.protos.IPv4TcpMsg other = (com.repcomm.sneknet.protos.IPv4TcpMsg) obj;

    if (getSrcPort()
        != other.getSrcPort()) return false;
    if (getDstPort()
        != other.getDstPort()) return false;
    if (hasIPv4() != other.hasIPv4()) return false;
    if (hasIPv4()) {
      if (!getIPv4()
          .equals(other.getIPv4())) return false;
    }
    if (getIsSyn()
        != other.getIsSyn()) return false;
    if (getIsAck()
        != other.getIsAck()) return false;
    if (getSeqId()
        != other.getSeqId()) return false;
    if (!getPayload()
        .equals(other.getPayload())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + SRCPORT_FIELD_NUMBER;
    hash = (53 * hash) + getSrcPort();
    hash = (37 * hash) + DSTPORT_FIELD_NUMBER;
    hash = (53 * hash) + getDstPort();
    if (hasIPv4()) {
      hash = (37 * hash) + IPV4_FIELD_NUMBER;
      hash = (53 * hash) + getIPv4().hashCode();
    }
    hash = (37 * hash) + ISSYN_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getIsSyn());
    hash = (37 * hash) + ISACK_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getIsAck());
    hash = (37 * hash) + SEQID_FIELD_NUMBER;
    hash = (53 * hash) + getSeqId();
    hash = (37 * hash) + PAYLOAD_FIELD_NUMBER;
    hash = (53 * hash) + getPayload().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.repcomm.sneknet.protos.IPv4TcpMsg parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.repcomm.sneknet.protos.IPv4TcpMsg prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code ipc.IPv4TcpMsg}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ipc.IPv4TcpMsg)
      com.repcomm.sneknet.protos.IPv4TcpMsgOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.repcomm.sneknet.protos.IPC.internal_static_ipc_IPv4TcpMsg_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.repcomm.sneknet.protos.IPC.internal_static_ipc_IPv4TcpMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.repcomm.sneknet.protos.IPv4TcpMsg.class, com.repcomm.sneknet.protos.IPv4TcpMsg.Builder.class);
    }

    // Construct using com.repcomm.sneknet.protos.IPv4TcpMsg.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage
              .alwaysUseFieldBuilders) {
        getIPv4FieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      srcPort_ = 0;
      dstPort_ = 0;
      iPv4_ = null;
      if (iPv4Builder_ != null) {
        iPv4Builder_.dispose();
        iPv4Builder_ = null;
      }
      isSyn_ = false;
      isAck_ = false;
      seqId_ = 0;
      payload_ = com.google.protobuf.ByteString.EMPTY;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.repcomm.sneknet.protos.IPC.internal_static_ipc_IPv4TcpMsg_descriptor;
    }

    @java.lang.Override
    public com.repcomm.sneknet.protos.IPv4TcpMsg getDefaultInstanceForType() {
      return com.repcomm.sneknet.protos.IPv4TcpMsg.getDefaultInstance();
    }

    @java.lang.Override
    public com.repcomm.sneknet.protos.IPv4TcpMsg build() {
      com.repcomm.sneknet.protos.IPv4TcpMsg result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.repcomm.sneknet.protos.IPv4TcpMsg buildPartial() {
      com.repcomm.sneknet.protos.IPv4TcpMsg result = new com.repcomm.sneknet.protos.IPv4TcpMsg(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.repcomm.sneknet.protos.IPv4TcpMsg result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.srcPort_ = srcPort_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.dstPort_ = dstPort_;
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.iPv4_ = iPv4Builder_ == null
            ? iPv4_
            : iPv4Builder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.isSyn_ = isSyn_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.isAck_ = isAck_;
      }
      if (((from_bitField0_ & 0x00000020) != 0)) {
        result.seqId_ = seqId_;
      }
      if (((from_bitField0_ & 0x00000040) != 0)) {
        result.payload_ = payload_;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.repcomm.sneknet.protos.IPv4TcpMsg) {
        return mergeFrom((com.repcomm.sneknet.protos.IPv4TcpMsg)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.repcomm.sneknet.protos.IPv4TcpMsg other) {
      if (other == com.repcomm.sneknet.protos.IPv4TcpMsg.getDefaultInstance()) return this;
      if (other.getSrcPort() != 0) {
        setSrcPort(other.getSrcPort());
      }
      if (other.getDstPort() != 0) {
        setDstPort(other.getDstPort());
      }
      if (other.hasIPv4()) {
        mergeIPv4(other.getIPv4());
      }
      if (other.getIsSyn() != false) {
        setIsSyn(other.getIsSyn());
      }
      if (other.getIsAck() != false) {
        setIsAck(other.getIsAck());
      }
      if (other.getSeqId() != 0) {
        setSeqId(other.getSeqId());
      }
      if (other.getPayload() != com.google.protobuf.ByteString.EMPTY) {
        setPayload(other.getPayload());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              srcPort_ = input.readInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              dstPort_ = input.readInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              input.readMessage(
                  getIPv4FieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 32: {
              isSyn_ = input.readBool();
              bitField0_ |= 0x00000008;
              break;
            } // case 32
            case 40: {
              isAck_ = input.readBool();
              bitField0_ |= 0x00000010;
              break;
            } // case 40
            case 48: {
              seqId_ = input.readUInt32();
              bitField0_ |= 0x00000020;
              break;
            } // case 48
            case 58: {
              payload_ = input.readBytes();
              bitField0_ |= 0x00000040;
              break;
            } // case 58
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private int srcPort_ ;
    /**
     * <code>int32 SrcPort = 1;</code>
     * @return The srcPort.
     */
    @java.lang.Override
    public int getSrcPort() {
      return srcPort_;
    }
    /**
     * <code>int32 SrcPort = 1;</code>
     * @param value The srcPort to set.
     * @return This builder for chaining.
     */
    public Builder setSrcPort(int value) {

      srcPort_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>int32 SrcPort = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSrcPort() {
      bitField0_ = (bitField0_ & ~0x00000001);
      srcPort_ = 0;
      onChanged();
      return this;
    }

    private int dstPort_ ;
    /**
     * <code>int32 DstPort = 2;</code>
     * @return The dstPort.
     */
    @java.lang.Override
    public int getDstPort() {
      return dstPort_;
    }
    /**
     * <code>int32 DstPort = 2;</code>
     * @param value The dstPort to set.
     * @return This builder for chaining.
     */
    public Builder setDstPort(int value) {

      dstPort_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>int32 DstPort = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearDstPort() {
      bitField0_ = (bitField0_ & ~0x00000002);
      dstPort_ = 0;
      onChanged();
      return this;
    }

    private com.repcomm.sneknet.protos.IPv4Info iPv4_;
    private com.google.protobuf.SingleFieldBuilder<
        com.repcomm.sneknet.protos.IPv4Info, com.repcomm.sneknet.protos.IPv4Info.Builder, com.repcomm.sneknet.protos.IPv4InfoOrBuilder> iPv4Builder_;
    /**
     * <code>.ipc.IPv4Info IPv4 = 3;</code>
     * @return Whether the iPv4 field is set.
     */
    public boolean hasIPv4() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <code>.ipc.IPv4Info IPv4 = 3;</code>
     * @return The iPv4.
     */
    public com.repcomm.sneknet.protos.IPv4Info getIPv4() {
      if (iPv4Builder_ == null) {
        return iPv4_ == null ? com.repcomm.sneknet.protos.IPv4Info.getDefaultInstance() : iPv4_;
      } else {
        return iPv4Builder_.getMessage();
      }
    }
    /**
     * <code>.ipc.IPv4Info IPv4 = 3;</code>
     */
    public Builder setIPv4(com.repcomm.sneknet.protos.IPv4Info value) {
      if (iPv4Builder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        iPv4_ = value;
      } else {
        iPv4Builder_.setMessage(value);
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>.ipc.IPv4Info IPv4 = 3;</code>
     */
    public Builder setIPv4(
        com.repcomm.sneknet.protos.IPv4Info.Builder builderForValue) {
      if (iPv4Builder_ == null) {
        iPv4_ = builderForValue.build();
      } else {
        iPv4Builder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>.ipc.IPv4Info IPv4 = 3;</code>
     */
    public Builder mergeIPv4(com.repcomm.sneknet.protos.IPv4Info value) {
      if (iPv4Builder_ == null) {
        if (((bitField0_ & 0x00000004) != 0) &&
          iPv4_ != null &&
          iPv4_ != com.repcomm.sneknet.protos.IPv4Info.getDefaultInstance()) {
          getIPv4Builder().mergeFrom(value);
        } else {
          iPv4_ = value;
        }
      } else {
        iPv4Builder_.mergeFrom(value);
      }
      if (iPv4_ != null) {
        bitField0_ |= 0x00000004;
        onChanged();
      }
      return this;
    }
    /**
     * <code>.ipc.IPv4Info IPv4 = 3;</code>
     */
    public Builder clearIPv4() {
      bitField0_ = (bitField0_ & ~0x00000004);
      iPv4_ = null;
      if (iPv4Builder_ != null) {
        iPv4Builder_.dispose();
        iPv4Builder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.ipc.IPv4Info IPv4 = 3;</code>
     */
    public com.repcomm.sneknet.protos.IPv4Info.Builder getIPv4Builder() {
      bitField0_ |= 0x00000004;
      onChanged();
      return getIPv4FieldBuilder().getBuilder();
    }
    /**
     * <code>.ipc.IPv4Info IPv4 = 3;</code>
     */
    public com.repcomm.sneknet.protos.IPv4InfoOrBuilder getIPv4OrBuilder() {
      if (iPv4Builder_ != null) {
        return iPv4Builder_.getMessageOrBuilder();
      } else {
        return iPv4_ == null ?
            com.repcomm.sneknet.protos.IPv4Info.getDefaultInstance() : iPv4_;
      }
    }
    /**
     * <code>.ipc.IPv4Info IPv4 = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        com.repcomm.sneknet.protos.IPv4Info, com.repcomm.sneknet.protos.IPv4Info.Builder, com.repcomm.sneknet.protos.IPv4InfoOrBuilder> 
        getIPv4FieldBuilder() {
      if (iPv4Builder_ == null) {
        iPv4Builder_ = new com.google.protobuf.SingleFieldBuilder<
            com.repcomm.sneknet.protos.IPv4Info, com.repcomm.sneknet.protos.IPv4Info.Builder, com.repcomm.sneknet.protos.IPv4InfoOrBuilder>(
                getIPv4(),
                getParentForChildren(),
                isClean());
        iPv4_ = null;
      }
      return iPv4Builder_;
    }

    private boolean isSyn_ ;
    /**
     * <code>bool IsSyn = 4;</code>
     * @return The isSyn.
     */
    @java.lang.Override
    public boolean getIsSyn() {
      return isSyn_;
    }
    /**
     * <code>bool IsSyn = 4;</code>
     * @param value The isSyn to set.
     * @return This builder for chaining.
     */
    public Builder setIsSyn(boolean value) {

      isSyn_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <code>bool IsSyn = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearIsSyn() {
      bitField0_ = (bitField0_ & ~0x00000008);
      isSyn_ = false;
      onChanged();
      return this;
    }

    private boolean isAck_ ;
    /**
     * <code>bool IsAck = 5;</code>
     * @return The isAck.
     */
    @java.lang.Override
    public boolean getIsAck() {
      return isAck_;
    }
    /**
     * <code>bool IsAck = 5;</code>
     * @param value The isAck to set.
     * @return This builder for chaining.
     */
    public Builder setIsAck(boolean value) {

      isAck_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <code>bool IsAck = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearIsAck() {
      bitField0_ = (bitField0_ & ~0x00000010);
      isAck_ = false;
      onChanged();
      return this;
    }

    private int seqId_ ;
    /**
     * <code>uint32 SeqId = 6;</code>
     * @return The seqId.
     */
    @java.lang.Override
    public int getSeqId() {
      return seqId_;
    }
    /**
     * <code>uint32 SeqId = 6;</code>
     * @param value The seqId to set.
     * @return This builder for chaining.
     */
    public Builder setSeqId(int value) {

      seqId_ = value;
      bitField0_ |= 0x00000020;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 SeqId = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearSeqId() {
      bitField0_ = (bitField0_ & ~0x00000020);
      seqId_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes Payload = 7;</code>
     * @return The payload.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getPayload() {
      return payload_;
    }
    /**
     * <code>bytes Payload = 7;</code>
     * @param value The payload to set.
     * @return This builder for chaining.
     */
    public Builder setPayload(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      payload_ = value;
      bitField0_ |= 0x00000040;
      onChanged();
      return this;
    }
    /**
     * <code>bytes Payload = 7;</code>
     * @return This builder for chaining.
     */
    public Builder clearPayload() {
      bitField0_ = (bitField0_ & ~0x00000040);
      payload_ = getDefaultInstance().getPayload();
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:ipc.IPv4TcpMsg)
  }

  // @@protoc_insertion_point(class_scope:ipc.IPv4TcpMsg)
  private static final com.repcomm.sneknet.protos.IPv4TcpMsg DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.repcomm.sneknet.protos.IPv4TcpMsg();
  }

  public static com.repcomm.sneknet.protos.IPv4TcpMsg getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<IPv4TcpMsg>
      PARSER = new com.google.protobuf.AbstractParser<IPv4TcpMsg>() {
    @java.lang.Override
    public IPv4TcpMsg parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<IPv4TcpMsg> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<IPv4TcpMsg> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.repcomm.sneknet.protos.IPv4TcpMsg getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

