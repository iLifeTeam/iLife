package com.ilife.zhihu.crawller;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.30.2)",
    comments = "Source: zhihu.proto")
public final class ZhihuServiceGrpc {

  private ZhihuServiceGrpc() {}

  public static final String SERVICE_NAME = "ZhihuService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Zhihu.LoginRequest,
      Zhihu.LoginResponse> getLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Login",
      requestType = Zhihu.LoginRequest.class,
      responseType = Zhihu.LoginResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Zhihu.LoginRequest,
      Zhihu.LoginResponse> getLoginMethod() {
    io.grpc.MethodDescriptor<Zhihu.LoginRequest, Zhihu.LoginResponse> getLoginMethod;
    if ((getLoginMethod = ZhihuServiceGrpc.getLoginMethod) == null) {
      synchronized (ZhihuServiceGrpc.class) {
        if ((getLoginMethod = ZhihuServiceGrpc.getLoginMethod) == null) {
          ZhihuServiceGrpc.getLoginMethod = getLoginMethod =
              io.grpc.MethodDescriptor.<Zhihu.LoginRequest, Zhihu.LoginResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Zhihu.LoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Zhihu.LoginResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZhihuServiceMethodDescriptorSupplier("Login"))
              .build();
        }
      }
    }
    return getLoginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Zhihu.ActivitiyRequest,
      Zhihu.ActivityResponse> getGetActivityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetActivity",
      requestType = Zhihu.ActivitiyRequest.class,
      responseType = Zhihu.ActivityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Zhihu.ActivitiyRequest,
      Zhihu.ActivityResponse> getGetActivityMethod() {
    io.grpc.MethodDescriptor<Zhihu.ActivitiyRequest, Zhihu.ActivityResponse> getGetActivityMethod;
    if ((getGetActivityMethod = ZhihuServiceGrpc.getGetActivityMethod) == null) {
      synchronized (ZhihuServiceGrpc.class) {
        if ((getGetActivityMethod = ZhihuServiceGrpc.getGetActivityMethod) == null) {
          ZhihuServiceGrpc.getGetActivityMethod = getGetActivityMethod =
              io.grpc.MethodDescriptor.<Zhihu.ActivitiyRequest, Zhihu.ActivityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetActivity"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Zhihu.ActivitiyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Zhihu.ActivityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZhihuServiceMethodDescriptorSupplier("GetActivity"))
              .build();
        }
      }
    }
    return getGetActivityMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ZhihuServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZhihuServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZhihuServiceStub>() {
        @Override
        public ZhihuServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZhihuServiceStub(channel, callOptions);
        }
      };
    return ZhihuServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ZhihuServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZhihuServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZhihuServiceBlockingStub>() {
        @Override
        public ZhihuServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZhihuServiceBlockingStub(channel, callOptions);
        }
      };
    return ZhihuServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ZhihuServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZhihuServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZhihuServiceFutureStub>() {
        @Override
        public ZhihuServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZhihuServiceFutureStub(channel, callOptions);
        }
      };
    return ZhihuServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ZhihuServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void login(Zhihu.LoginRequest request,
                      io.grpc.stub.StreamObserver<Zhihu.LoginResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getLoginMethod(), responseObserver);
    }

    /**
     */
    public void getActivity(Zhihu.ActivitiyRequest request,
                            io.grpc.stub.StreamObserver<Zhihu.ActivityResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetActivityMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLoginMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Zhihu.LoginRequest,
                Zhihu.LoginResponse>(
                  this, METHODID_LOGIN)))
          .addMethod(
            getGetActivityMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Zhihu.ActivitiyRequest,
                Zhihu.ActivityResponse>(
                  this, METHODID_GET_ACTIVITY)))
          .build();
    }
  }

  /**
   */
  public static final class ZhihuServiceStub extends io.grpc.stub.AbstractAsyncStub<ZhihuServiceStub> {
    private ZhihuServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ZhihuServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZhihuServiceStub(channel, callOptions);
    }

    /**
     */
    public void login(Zhihu.LoginRequest request,
                      io.grpc.stub.StreamObserver<Zhihu.LoginResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getActivity(Zhihu.ActivitiyRequest request,
                            io.grpc.stub.StreamObserver<Zhihu.ActivityResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetActivityMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ZhihuServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ZhihuServiceBlockingStub> {
    private ZhihuServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ZhihuServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZhihuServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public Zhihu.LoginResponse login(Zhihu.LoginRequest request) {
      return blockingUnaryCall(
          getChannel(), getLoginMethod(), getCallOptions(), request);
    }

    /**
     */
    public Zhihu.ActivityResponse getActivity(Zhihu.ActivitiyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetActivityMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ZhihuServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ZhihuServiceFutureStub> {
    private ZhihuServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ZhihuServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZhihuServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Zhihu.LoginResponse> login(
        Zhihu.LoginRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Zhihu.ActivityResponse> getActivity(
        Zhihu.ActivitiyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetActivityMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN = 0;
  private static final int METHODID_GET_ACTIVITY = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ZhihuServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ZhihuServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOGIN:
          serviceImpl.login((Zhihu.LoginRequest) request,
              (io.grpc.stub.StreamObserver<Zhihu.LoginResponse>) responseObserver);
          break;
        case METHODID_GET_ACTIVITY:
          serviceImpl.getActivity((Zhihu.ActivitiyRequest) request,
              (io.grpc.stub.StreamObserver<Zhihu.ActivityResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ZhihuServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ZhihuServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Zhihu.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ZhihuService");
    }
  }

  private static final class ZhihuServiceFileDescriptorSupplier
      extends ZhihuServiceBaseDescriptorSupplier {
    ZhihuServiceFileDescriptorSupplier() {}
  }

  private static final class ZhihuServiceMethodDescriptorSupplier
      extends ZhihuServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ZhihuServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ZhihuServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ZhihuServiceFileDescriptorSupplier())
              .addMethod(getLoginMethod())
              .addMethod(getGetActivityMethod())
              .build();
        }
      }
    }
    return result;
  }
}
