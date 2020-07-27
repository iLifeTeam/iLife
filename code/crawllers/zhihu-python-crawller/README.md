

### generate python grpc stub
```
python -m grpc_tools.protoc -I./ --python_out=. --grpc_python_out=. ./service.proto
```

### 使用docker打包python程序

1. 创建Dockerfile
   1. 使用python:3.7-slim 比 python:3.7小很多, python:3.7-alpine更小，但是出现了找不到包错误。不知道有没有best-practice
   2. 将 Add Requirement, pip install 放在最前面（apt install也类似）,可以利用cache
2. docker build . -t zhihu-python-crawller:0.0 

注意：

1. 使用.dockerignore来去除无用文件

---

后续更新在/doc里面

