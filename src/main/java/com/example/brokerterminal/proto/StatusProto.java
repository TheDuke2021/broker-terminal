// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Status.proto

package com.example.brokerterminal.proto;

public final class StatusProto {
  private StatusProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_example_brokerterminal_proto_Status_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_example_brokerterminal_proto_Status_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014Status.proto\022 com.example.brokertermin" +
      "al.proto\032\022MessageEnums.proto\032\rAdvInfo.pr" +
      "oto\"\333\001\n\006Status\022:\n\004type\030\001 \001(\0162,.com.examp" +
      "le.brokerterminal.proto.StatusType\022\024\n\007de" +
      "tails\030\002 \001(\tH\000\210\001\001\022\025\n\010nextTime\030\003 \001(\003H\001\210\001\001\022" +
      "A\n\tadvStatus\030\004 \001(\0132).com.example.brokert" +
      "erminal.proto.AdvInfoH\002\210\001\001B\n\n\010_detailsB\013" +
      "\n\t_nextTimeB\014\n\n_advStatusB\021B\013StatusProto" +
      "H\001P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.example.brokerterminal.proto.MessageEnumsProto.getDescriptor(),
          com.example.brokerterminal.proto.AdvInfoProto.getDescriptor(),
        });
    internal_static_com_example_brokerterminal_proto_Status_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_example_brokerterminal_proto_Status_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_example_brokerterminal_proto_Status_descriptor,
        new java.lang.String[] { "Type", "Details", "NextTime", "AdvStatus", "Details", "NextTime", "AdvStatus", });
    com.example.brokerterminal.proto.MessageEnumsProto.getDescriptor();
    com.example.brokerterminal.proto.AdvInfoProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
