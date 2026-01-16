# Naven-Alpha2 构建问题快速修复指南

## 立即行动项

### 1. 修复构建系统（最高优先级）

当前阻塞: Fabric Loom + Gson 2.9.1 反射访问问题

#### 方案A: 升级到Fabric Loom 1.6.12（推荐）

编辑 `build.gradle`:
```groovy
plugins {
    id 'fabric-loom' version '1.6.12'  // 从 1.6-SNAPSHOT 改为 1.6.12
    id 'maven-publish'
    id 'eclipse'
    id 'idea'
}
```

然后运行:
```bash
rm -rf .gradle .fabric build
./gradlew build
```

#### 方案B: 升级到Fabric Loom 1.8+（需要Gradle 8.10）

步骤1: 升级Gradle

编辑 `gradle/wrapper/gradle-wrapper.properties`:
```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.10-bin.zip
```

步骤2: 升级Loom

编辑 `build.gradle`:
```groovy
plugins {
    id 'fabric-loom' version '1.8.0'
    id 'maven-publish'
    id 'eclipse'
    id 'idea'
}
```

步骤3: 清理并构建
```bash
rm -rf .gradle .fabric build
./gradlew build
```

---

## 预期的编译错误及修复方案

### 错误类型1: Packet类不存在

**错误示例:**
```
error: cannot find symbol
  symbol: class ClientboundMoveEntityPacket
```

**修复方案:**
在Minecraft 1.20.6中，`ClientboundMoveEntityPacket`已被拆分为多个具体的Packet类：

```java
// 旧代码（1.20.1及之前）
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;

// 新代码（1.20.6）
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacketPos;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacketPosRot;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacketRot;
```

需要检查Packet的实际类型并更新import和instanceof检查。

### 错误类型2: Packet字段访问失败

**错误示例:**
```
error: cannot find symbol
  symbol: method getId()
  location: variable packet of type ClientboundSetEntityMotionPacket
```

**修复方案:**
Minecraft 1.20.6可能将Packet的字段访问改为方法访问：

```java
// 旧代码
if (packet.getId() == mc.player.getId()) {
    Vec3 velocity = new Vec3(packet.getXa(), packet.getYa(), packet.getZa());
}

// 新代码（需要查看1.20.6的实际API）
Entity entity = (Entity) packet.getEntity(mc.level);
if (entity == mc.player) {
    Vec3 velocity = packet.getMotion();  // 假设的新方法名
}
```

### 错误类型3: Mixin目标方法找不到

**错误示例:**
```
[Mixin] Error applying mixin MixinLivingEntity -> LivingEntity:
  Cannot find method jumpFromGround in target class
```

**修复方案:**

步骤1: 生成1.20.6的源码
```bash
./gradlew genSources
```

步骤2: 检查目标类的实际方法名
```bash
# 查看生成的源码
find .gradle -name "LivingEntity.java" -type f | head -1
```

步骤3: 更新Mixin的target表达式
```java
// 旧代码
@Redirect(
    method = {"jumpFromGround"},
    at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;getYRot()F"
    )
)

// 新代码（假设方法名改变）
@Redirect(
    method = {"jumpFromGround"},  // 或新的方法名
    at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;getYRot()F"  // 或新的目标
    )
)
```

### 错误类型4: Accessor字段不存在

**错误示例:**
```
[Mixin] Error applying mixin ClientboundSetEntityMotionPacketAccessor:
  Cannot find field 'xa' in target class
```

**修复方案:**

步骤1: 查看Packet类的实际字段
```bash
# 使用Yarn mappings浏览器
# https://mappings.cephelo.dev/
# 搜索: ClientboundSetEntityMotionPacket
```

步骤2: 更新Accessor接口
```java
// 旧代码
@Interface("net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket")
@Mixin
public interface ClientboundSetEntityMotionPacketAccessor {
    @Accessor("xa")
    double getXa();

    @Accessor("ya")
    double getYa();

    @Accessor("za")
    double getZa();

    @Accessor("id")
    int getId();
}

// 新代码（假设使用方法而非字段）
@Interface("net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket")
@Mixin
public interface ClientboundSetEntityMotionPacketAccessor {
    // 假设的新方法
    Vec3 getMotion();
    int getEntityId();
}
```

---

## 修复顺序建议

1. **首先修复构建系统** - 不然后续无法编译
2. **修复NetworkUtils.java** - 这是最基础的工具类，其他模块依赖它
3. **修复Velocity.java** - 核心战斗模块，需要验证Packet API
4. **修复Blink.java, LongJump.java** - 其他移动模块
5. **修复Mixin Accessors** - Packet相关的Accessors
6. **修复其他Mixin** - 从基础到高级

---

## 有用的Gradle命令

```bash
# 生成源码
./gradlew genSources

# 清理构建
./gradlew clean

# 完整构建
./gradlew build

# 跳过测试构建
./gradlew build -x test

# 只编译Java代码
./gradlew compileJava

# 查看依赖树
./gradlew dependencies

# 查看详细的构建信息
./gradlew build --info

# 查看stack trace
./gradlew build --stacktrace
```

---

## 调试技巧

### 查看Minecraft 1.20.6的API

```bash
# 方法1: 使用Gradle生成的源码
./gradlew genSources
find .gradle/caches/fabric-loom -name "*.java" -path "*/net/minecraft/*" | grep "Packet"

# 方法2: 使用在线工具
# https://mappings.cephelo.dev/
# 搜索感兴趣的类名

# 方法3: 使用IDE的导航功能
# 在IDE中打开项目，跳转到Packet类的声明
```

### Mixin调试

```java
// 在Mixin中添加日志
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    private static final Logger LOGGER = LogManager.getLogger("NavenMixin");

    @Inject(method = "jumpFromGround", at = @At("HEAD"))
    private void onJump(CallbackInfo ci) {
        LOGGER.info("LivingEntity.jumpFromGround called");
        // 你的代码
    }
}
```

---

## 常见问题

### Q: 编译成功但游戏崩溃
A: 这通常是Mixin映射问题。检查日志中的Mixin错误，确保所有Mixin都能正确应用。

### Q: 某些模块不工作
A: 可能是Packet拦截失败。检查`NetworkUtils.java`和相关的Mixin，确保Packet被正确拦截。

### Q: 如何确认Minecraft版本
A: 运行游戏后，在主菜单按F3，查看版本信息。

---

## 获取帮助

### 官方资源
- Fabric文档: https://fabricmc.net/wiki/
- Fabric Discord: https://discord.gg/v6v4pMv
- Minecraft Wiki: https://minecraft.fandom.com/

### 社区资源
- Fabric Mod Development Discord: https://discord.gg/Vv9e4eW
- Reddit r/fabricmc: https://reddit.com/r/fabricmc

---

*更新时间: 2025-01-16*
*适用版本: Minecraft 1.20.6 + Fabric*
