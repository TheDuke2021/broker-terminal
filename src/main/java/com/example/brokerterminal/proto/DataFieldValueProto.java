// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: DataFieldValue.proto

package com.example.brokerterminal.proto;

public final class DataFieldValueProto {
  private DataFieldValueProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_example_brokerterminal_proto_DataFieldValue_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_example_brokerterminal_proto_DataFieldValue_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024DataFieldValue.proto\022 com.example.brok" +
      "erterminal.proto\032\016ValueRef.proto\"Z\n\016Data" +
      "FieldValue\022\r\n\005alias\030\001 \001(\t\0229\n\005value\030\002 \001(\013" +
      "2*.com.example.brokerterminal.proto.Valu" +
      "eRefB\031B\023DataFieldValueProtoH\001P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.example.brokerterminal.proto.ValueRefProto.getDescriptor(),
        });
    internal_static_com_example_brokerterminal_proto_DataFieldValue_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_example_brokerterminal_proto_DataFieldValue_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_example_brokerterminal_proto_DataFieldValue_descriptor,
        new java.lang.String[] { "Alias", "Value", });
    com.example.brokerterminal.proto.ValueRefProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
