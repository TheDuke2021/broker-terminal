// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Event.proto

package com.example.brokerterminal.proto;

/**
 * Protobuf type {@code com.example.brokerterminal.proto.Event}
 */
public final class Event extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.example.brokerterminal.proto.Event)
    EventOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Event.newBuilder() to construct.
  private Event(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Event() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Event();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.example.brokerterminal.proto.EventProto.internal_static_com_example_brokerterminal_proto_Event_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.example.brokerterminal.proto.EventProto.internal_static_com_example_brokerterminal_proto_Event_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.example.brokerterminal.proto.Event.class, com.example.brokerterminal.proto.Event.Builder.class);
  }

  public static final int STATUS_FIELD_NUMBER = 1;
  private com.example.brokerterminal.proto.Status status_;
  /**
   * <pre>
   * Статус
   * </pre>
   *
   * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
   * @return Whether the status field is set.
   */
  @java.lang.Override
  public boolean hasStatus() {
    return status_ != null;
  }
  /**
   * <pre>
   * Статус
   * </pre>
   *
   * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
   * @return The status.
   */
  @java.lang.Override
  public com.example.brokerterminal.proto.Status getStatus() {
    return status_ == null ? com.example.brokerterminal.proto.Status.getDefaultInstance() : status_;
  }
  /**
   * <pre>
   * Статус
   * </pre>
   *
   * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
   */
  @java.lang.Override
  public com.example.brokerterminal.proto.StatusOrBuilder getStatusOrBuilder() {
    return status_ == null ? com.example.brokerterminal.proto.Status.getDefaultInstance() : status_;
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
    if (status_ != null) {
      output.writeMessage(1, getStatus());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (status_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getStatus());
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
    if (!(obj instanceof com.example.brokerterminal.proto.Event)) {
      return super.equals(obj);
    }
    com.example.brokerterminal.proto.Event other = (com.example.brokerterminal.proto.Event) obj;

    if (hasStatus() != other.hasStatus()) return false;
    if (hasStatus()) {
      if (!getStatus()
          .equals(other.getStatus())) return false;
    }
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
    if (hasStatus()) {
      hash = (37 * hash) + STATUS_FIELD_NUMBER;
      hash = (53 * hash) + getStatus().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.example.brokerterminal.proto.Event parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.brokerterminal.proto.Event parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.brokerterminal.proto.Event parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.brokerterminal.proto.Event parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.brokerterminal.proto.Event parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.brokerterminal.proto.Event parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.brokerterminal.proto.Event parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.example.brokerterminal.proto.Event parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.example.brokerterminal.proto.Event parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.example.brokerterminal.proto.Event parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.example.brokerterminal.proto.Event parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.example.brokerterminal.proto.Event parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.example.brokerterminal.proto.Event prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.example.brokerterminal.proto.Event}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.example.brokerterminal.proto.Event)
      com.example.brokerterminal.proto.EventOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.example.brokerterminal.proto.EventProto.internal_static_com_example_brokerterminal_proto_Event_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.example.brokerterminal.proto.EventProto.internal_static_com_example_brokerterminal_proto_Event_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.example.brokerterminal.proto.Event.class, com.example.brokerterminal.proto.Event.Builder.class);
    }

    // Construct using com.example.brokerterminal.proto.Event.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      status_ = null;
      if (statusBuilder_ != null) {
        statusBuilder_.dispose();
        statusBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.example.brokerterminal.proto.EventProto.internal_static_com_example_brokerterminal_proto_Event_descriptor;
    }

    @java.lang.Override
    public com.example.brokerterminal.proto.Event getDefaultInstanceForType() {
      return com.example.brokerterminal.proto.Event.getDefaultInstance();
    }

    @java.lang.Override
    public com.example.brokerterminal.proto.Event build() {
      com.example.brokerterminal.proto.Event result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.example.brokerterminal.proto.Event buildPartial() {
      com.example.brokerterminal.proto.Event result = new com.example.brokerterminal.proto.Event(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.example.brokerterminal.proto.Event result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.status_ = statusBuilder_ == null
            ? status_
            : statusBuilder_.build();
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.example.brokerterminal.proto.Event) {
        return mergeFrom((com.example.brokerterminal.proto.Event)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.example.brokerterminal.proto.Event other) {
      if (other == com.example.brokerterminal.proto.Event.getDefaultInstance()) return this;
      if (other.hasStatus()) {
        mergeStatus(other.getStatus());
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
            case 10: {
              input.readMessage(
                  getStatusFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
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

    private com.example.brokerterminal.proto.Status status_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.example.brokerterminal.proto.Status, com.example.brokerterminal.proto.Status.Builder, com.example.brokerterminal.proto.StatusOrBuilder> statusBuilder_;
    /**
     * <pre>
     * Статус
     * </pre>
     *
     * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
     * @return Whether the status field is set.
     */
    public boolean hasStatus() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <pre>
     * Статус
     * </pre>
     *
     * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
     * @return The status.
     */
    public com.example.brokerterminal.proto.Status getStatus() {
      if (statusBuilder_ == null) {
        return status_ == null ? com.example.brokerterminal.proto.Status.getDefaultInstance() : status_;
      } else {
        return statusBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Статус
     * </pre>
     *
     * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
     */
    public Builder setStatus(com.example.brokerterminal.proto.Status value) {
      if (statusBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        status_ = value;
      } else {
        statusBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Статус
     * </pre>
     *
     * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
     */
    public Builder setStatus(
        com.example.brokerterminal.proto.Status.Builder builderForValue) {
      if (statusBuilder_ == null) {
        status_ = builderForValue.build();
      } else {
        statusBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Статус
     * </pre>
     *
     * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
     */
    public Builder mergeStatus(com.example.brokerterminal.proto.Status value) {
      if (statusBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          status_ != null &&
          status_ != com.example.brokerterminal.proto.Status.getDefaultInstance()) {
          getStatusBuilder().mergeFrom(value);
        } else {
          status_ = value;
        }
      } else {
        statusBuilder_.mergeFrom(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Статус
     * </pre>
     *
     * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
     */
    public Builder clearStatus() {
      bitField0_ = (bitField0_ & ~0x00000001);
      status_ = null;
      if (statusBuilder_ != null) {
        statusBuilder_.dispose();
        statusBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Статус
     * </pre>
     *
     * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
     */
    public com.example.brokerterminal.proto.Status.Builder getStatusBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getStatusFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Статус
     * </pre>
     *
     * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
     */
    public com.example.brokerterminal.proto.StatusOrBuilder getStatusOrBuilder() {
      if (statusBuilder_ != null) {
        return statusBuilder_.getMessageOrBuilder();
      } else {
        return status_ == null ?
            com.example.brokerterminal.proto.Status.getDefaultInstance() : status_;
      }
    }
    /**
     * <pre>
     * Статус
     * </pre>
     *
     * <code>.com.example.brokerterminal.proto.Status status = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.example.brokerterminal.proto.Status, com.example.brokerterminal.proto.Status.Builder, com.example.brokerterminal.proto.StatusOrBuilder> 
        getStatusFieldBuilder() {
      if (statusBuilder_ == null) {
        statusBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.example.brokerterminal.proto.Status, com.example.brokerterminal.proto.Status.Builder, com.example.brokerterminal.proto.StatusOrBuilder>(
                getStatus(),
                getParentForChildren(),
                isClean());
        status_ = null;
      }
      return statusBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.example.brokerterminal.proto.Event)
  }

  // @@protoc_insertion_point(class_scope:com.example.brokerterminal.proto.Event)
  private static final com.example.brokerterminal.proto.Event DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.example.brokerterminal.proto.Event();
  }

  public static com.example.brokerterminal.proto.Event getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Event>
      PARSER = new com.google.protobuf.AbstractParser<Event>() {
    @java.lang.Override
    public Event parsePartialFrom(
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

  public static com.google.protobuf.Parser<Event> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Event> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.example.brokerterminal.proto.Event getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

