// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ValueRef.proto

package com.example.brokerterminal.proto;

public interface ValueRefOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.example.brokerterminal.proto.ValueRef)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Тип данных значения
   * </pre>
   *
   * <code>.com.example.brokerterminal.proto.DataType dataType = 1;</code>
   * @return The enum numeric value on the wire for dataType.
   */
  int getDataTypeValue();
  /**
   * <pre>
   * Тип данных значения
   * </pre>
   *
   * <code>.com.example.brokerterminal.proto.DataType dataType = 1;</code>
   * @return The dataType.
   */
  com.example.brokerterminal.proto.MessageEnumsProto.DataType getDataType();

  /**
   * <pre>
   * Формат строкового представления значения
   * </pre>
   *
   * <code>optional string format = 2;</code>
   * @return Whether the format field is set.
   */
  boolean hasFormat();
  /**
   * <pre>
   * Формат строкового представления значения
   * </pre>
   *
   * <code>optional string format = 2;</code>
   * @return The format.
   */
  java.lang.String getFormat();
  /**
   * <pre>
   * Формат строкового представления значения
   * </pre>
   *
   * <code>optional string format = 2;</code>
   * @return The bytes for format.
   */
  com.google.protobuf.ByteString
      getFormatBytes();

  /**
   * <pre>
   * Значение
   * </pre>
   *
   * <code>optional string value = 3;</code>
   * @return Whether the value field is set.
   */
  boolean hasValue();
  /**
   * <pre>
   * Значение
   * </pre>
   *
   * <code>optional string value = 3;</code>
   * @return The value.
   */
  java.lang.String getValue();
  /**
   * <pre>
   * Значение
   * </pre>
   *
   * <code>optional string value = 3;</code>
   * @return The bytes for value.
   */
  com.google.protobuf.ByteString
      getValueBytes();
}
