# Docker 和 PostgreSQL 完整配置流程

## 一、Docker 安装（Windows）

### 1. 下载 Docker Desktop
- 访问 [Docker官网](https://www.docker.com/products/docker-desktop)
- 点击 "Download for Windows"
- 下载 Docker Desktop Installer

### 2. 安装 Docker
- 双击安装程序 `Docker Desktop Installer.exe`
- 勾选 "Install required Windows components for WSL 2"（推荐）
- 完成安装后重启电脑

### 3. 验证安装
```powershell
docker --version
docker run hello-world
```

---

## 二、使用 Docker 配置 PostgreSQL

### 方案一：直接运行容器（简单）

#### 快速启动命令
```powershell
docker run --name pg-db -e POSTGRES_PASSWORD=your_password -e POSTGRES_DB=your_dbname -p 5432:5432 -d postgres:latest
```

#### 参数说明
- `--name pg-db` - 容器名称
- `-e POSTGRES_PASSWORD=your_password` - 数据库密码
- `-e POSTGRES_DB=your_dbname` - 创建的数据库名
- `-p 5432:5432` - 端口映射（主机端口:容器端口）
- `-d` - 后台运行
- `postgres:latest` - 使用最新的PostgreSQL镜像

#### 验证容器运行
```powershell
docker ps
```

---

### 方案二：使用 Docker Compose（推荐用于项目）

#### 创建 docker-compose.yml
在项目根目录创建 `docker-compose.yml` 文件：

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    container_name: tlias-postgres
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres123
      POSTGRES_DB: tlias_db
      TZ: Asia/Shanghai
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql  # 自动执行SQL脚本
    restart: always
    networks:
      - tlias-network

volumes:
  postgres_data:

networks:
  tlias-network:
    driver: bridge
```

#### 启动容器
```powershell
docker-compose up -d
```

#### 停止容器
```powershell
docker-compose down
```

#### 查看日志
```powershell
docker-compose logs -f postgres
```

---

## 三、连接 PostgreSQL

### 1. 命令行连接
```powershell
docker exec -it pg-db psql -U postgres
```

### 2. 使用图形化工具
推荐工具：**DBeaver**、**pgAdmin**、**DataGrip** 等

连接信息：
- **主机**：`localhost` 或 `127.0.0.1`
- **端口**：`5432`
- **用户**：`postgres`
- **密码**：你设置的密码（例如：`postgres123`）
- **数据库**：`tlias_db`

### 3. Java 项目连接
在 `application.yml` 中配置：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/tlias_db
    username: postgres
    password: postgres123
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

或者在 `application.properties` 中：
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tlias_db
spring.datasource.username=postgres
spring.datasource.password=postgres123
spring.datasource.driver-class-name=org.postgresql.Driver
```

---

## 四、常用 Docker 命令

| 命令 | 功能 |
|------|------|
| `docker ps` | 查看运行中的容器 |
| `docker ps -a` | 查看所有容器 |
| `docker logs 容器名` | 查看日志 |
| `docker logs -f 容器名` | 持续查看日志 |
| `docker stop 容器名` | 停止容器 |
| `docker start 容器名` | 启动容器 |
| `docker restart 容器名` | 重启容器 |
| `docker rm 容器名` | 删除容器 |
| `docker exec -it 容器名 bash` | 进入容器 |
| `docker inspect 容器名` | 查看容器详细信息 |

---

## 五、初始化数据库脚本

### 创建 init.sql（可选）
在项目根目录创建 `init.sql`，Docker 会在启动时自动执行：

```sql
-- 创建架构
CREATE SCHEMA IF NOT EXISTS tlias;

-- 创建部门表
CREATE TABLE IF NOT EXISTS dept (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建员工表
CREATE TABLE IF NOT EXISTS emp (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    dept_id INTEGER REFERENCES dept(id),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建班级表
CREATE TABLE IF NOT EXISTS clazz (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建学生表
CREATE TABLE IF NOT EXISTS student (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    clazz_id INTEGER REFERENCES clazz(id),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入示例数据
INSERT INTO dept (name) VALUES ('研发部');
INSERT INTO dept (name) VALUES ('市场部');
INSERT INTO dept (name) VALUES ('销售部');
```

---

## 六、常见问题解决

### 问题1：端口已被占用
```powershell
# 找出占用5432端口的进程
netstat -ano | findstr :5432

# 修改docker-compose.yml中的端口映射
# 将 "5432:5432" 改为 "5433:5432"
```

### 问题2：容器无法启动
```powershell
# 查看详细错误日志
docker logs postgres_container_name

# 检查是否缺少必要的环境变量
docker inspect postgres_container_name
```

### 问题3：连接超时
- 确保容器正在运行：`docker ps`
- 确保防火墙没有阻止5432端口
- 确保PostgreSQL服务已启动

### 问题4：权限不足
- 确保Docker Desktop已启动
- 在PowerShell中以管理员身份运行命令

---

## 七、完整启动流程总结

1. **安装Docker Desktop**
   ```powershell
   # 下载并安装，重启电脑
   ```

2. **创建docker-compose.yml**
   ```powershell
   # 在项目根目录创建文件
   ```

3. **启动PostgreSQL**
   ```powershell
   cd 项目根目录
   docker-compose up -d
   ```

4. **验证连接**
   ```powershell
   docker ps
   docker logs tlias-postgres
   ```

5. **在应用中配置数据库**
   ```yaml
   # 修改application.yml
   ```

6. **启动Spring Boot应用**
   ```powershell
   # 运行应用，通常会自动创建表结构
   ```

---

## 八、数据持久化和备份

### 查看数据卷
```powershell
docker volume ls
```

### 备份数据库
```powershell
docker exec tlias-postgres pg_dump -U postgres tlias_db > backup.sql
```

### 恢复数据库
```powershell
docker exec -i tlias-postgres psql -U postgres tlias_db < backup.sql
```

---

## 参考资源

- [Docker官方文档](https://docs.docker.com/)
- [PostgreSQL官方文档](https://www.postgresql.org/docs/)
- [Docker Compose官方文档](https://docs.docker.com/compose/)

---

## 本地配置好PG数据库后如何部署在docker上

### 一、前置条件

- 本地已有运行正常的PostgreSQL数据库
- 已安装Docker Desktop
- 确保了解本地数据库的：用户名、密码、数据库名、表结构等信息

### 二、备份本地数据库

#### 1. 使用 pg_dump 备份
```powershell
# 备份整个数据库
pg_dump -U postgres -d tlias_db > tlias_db_backup.sql

# 如果PostgreSQL不在环境变量中，使用完整路径
# "C:\Program Files\PostgreSQL\15\bin\pg_dump" -U postgres -d tlias_db > tlias_db_backup.sql
```

#### 2. 备份特定表（可选）
```powershell
pg_dump -U postgres -d tlias_db -t dept -t emp -t student > table_backup.sql
```

### 三、导出本地数据为初始化脚本

#### 方案1：导出结构（推荐）
```powershell
# 导出数据库结构和数据
pg_dump -U postgres -d tlias_db > init.sql

# 导出数据库结构（不包括数据）
pg_dump -U postgres -d tlias_db -s > structure.sql

# 导出数据（不包括表结构）
pg_dump -U postgres -d tlias_db -a > data.sql
```

#### 方案2：生成 init.sql 供 Docker 使用
```powershell
# 结合创建数据库语句和表结构
echo "CREATE DATABASE tlias_db;" > init.sql
pg_dump -U postgres -d tlias_db >> init.sql
```

### 四、创建新的 Docker PostgreSQL 实例

#### 1. 停止本地 PostgreSQL（可选）
```powershell
# 如果仍需本地数据库，可跳过此步
# Windows 服务中停止
net stop postgresql-x64-15

# 或在PostgreSQL bin目录中
"C:\Program Files\PostgreSQL\15\bin\pg_ctl" stop
```

#### 2. 准备目录结构
```powershell
# 在项目根目录创建必要文件夹
mkdir docker
cd docker
mkdir postgres_data
mkdir init
```

#### 3. 放置初始化脚本
```powershell
# 将备份的 init.sql 放入 init 文件夹
# 或复制粘贴已有的 pgdump 输出
cp ../tlias_db_backup.sql ./init/init.sql
```

### 五、创建 Docker Compose 配置导入本地数据

#### 升级版 docker-compose.yml（包含本地数据）
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: tlias-postgres
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres123
      POSTGRES_DB: tlias_db
      TZ: Asia/Shanghai
    ports:
      - "5432:5432"
    volumes:
      # 挂载本地备份文件为初始化脚本
      - ./docker/init/init.sql:/docker-entrypoint-initdb.d/01-init.sql
      # 持久化数据卷
      - postgres_data:/var/lib/postgresql/data
    restart: always
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - tlias-network

volumes:
  postgres_data:

networks:
  tlias-network:
    driver: bridge
```

### 六、启动 Docker 并导入数据

#### 1. 启动容器
```powershell
# 进入项目根目录
cd d:\Program Files\VSCode\VSCode_project\demo\tlias

# 启动容器（自动执行 init.sql）
docker-compose up -d

# 查看启动日志
docker-compose logs -f postgres
```

#### 2. 验证数据导入
```powershell
# 进入容器
docker exec -it tlias-postgres psql -U postgres -d tlias_db

# 在 psql 中执行
\dt                    # 列出所有表
SELECT COUNT(*) FROM dept;  # 查看数据
\q                     # 退出
```

#### 3. 通过命令行验证（如果已启动）
```powershell
# 或者直接在主机执行
psql -h localhost -U postgres -d tlias_db -c "SELECT COUNT(*) FROM dept;"
```

### 七、如果已有正在运行的 Docker PostgreSQL

#### 导入本地数据到现有容器
```powershell
# 方法1：使用管道直接导入
cat ./docker/init/init.sql | docker exec -i tlias-postgres psql -U postgres tlias_db

# 方法2：复制备份文件然后导入
docker cp tlias_db_backup.sql tlias-postgres:/backup.sql
docker exec tlias-postgres psql -U postgres -d tlias_db -f /backup.sql
```

### 八、迁移本地应用连接字符串

#### 修改 application.yml
```yaml
spring:
  datasource:
    # 从本地连接改为 Docker 容器连接
    url: jdbc:postgresql://localhost:5432/tlias_db
    username: postgres
    password: postgres123
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
```

#### 如果应用也在 Docker 中运行
```yaml
spring:
  datasource:
    # 使用服务名代替 localhost
    url: jdbc:postgresql://postgres:5432/tlias_db
    username: postgres
    password: postgres123
    driver-class-name: org.postgresql.Driver
```

### 九、完整迁移流程检查清单

- [x] 备份本地 PostgreSQL 数据（`pg_dump`）
- [x] 创建 Docker 目录结构
- [x] 放置 `init.sql` 到 Docker 初始化目录
- [x] 编写 `docker-compose.yml`
- [x] 启动 Docker PostgreSQL（`docker-compose up -d`）
- [x] 验证容器运行状态（`docker ps`）
- [x] 检查数据是否成功导入
- [x] 更新应用配置文件
- [x] 启动 Spring Boot 应用
- [x] 测试应用连接数据库

### 十、故障排除

#### 数据未导入或导入部分失败
```powershell
# 查看详细日志
docker logs tlias-postgres

# 检查 init.sql 语法
psql -f ./docker/init/init.sql -U postgres

# 删除容器重新开始
docker-compose down -v
docker-compose up -d
```

#### 连接拒绝
```powershell
# 确保容器已启动并就绪
docker ps
docker exec tlias-postgres pg_isready

# 检查防火墙或端口占用
netstat -ano | findstr :5432
```

#### 编码问题（乱码）
在 `docker-compose.yml` 中添加：
```yaml
environment:
  POSTGRES_INITDB_ARGS: "-c client_encoding=UTF8"
```

### 十一、数据同步方案

#### 定期备份方案
```powershell
# 创建 backup.sh 脚本
$timestamp = Get-Date -Format "yyyy-MM-dd_HH-mm-ss"
docker exec tlias-postgres pg_dump -U postgres tlias_db > "backups/tlias_db_$timestamp.sql"
```

#### Docker 与本地数据库同步
```powershell
# 从 Docker 导出最新数据
docker exec tlias-postgres pg_dump -U postgres tlias_db > latest_backup.sql

# 导入到本地
psql -U postgres -d tlias_db -f latest_backup.sql
```

### 十二、高级配置

#### 使用命名卷而非挂载目录
```yaml
services:
  postgres:
    volumes:
      - postgres_named_volume:/var/lib/postgresql/data

volumes:
  postgres_named_volume:
    driver: local
```

#### 自动备份脚本
创建 `backup-db.sh`：
```bash
#!/bin/bash
BACKUP_DIR="./backups"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
docker exec tlias-postgres pg_dump -U postgres tlias_db > "$BACKUP_DIR/backup_$TIMESTAMP.sql"
echo "备份完成: $BACKUP_DIR/backup_$TIMESTAMP.sql"
```

运行备份：
```powershell
bash backup-db.sh
```