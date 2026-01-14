# Naven-Alpha2

一个基于Naven-Alpha开源的修改客户端，没有验证，如果你想使用，请自己构建

原项目开源地址： https://github.com/jiuxianqwq/NavenAlpha

## 🚀 特性

### 核心功能

- **模块化架构**: 可扩展的模块系统，支持动态加载和管理
- **事件驱动系统**: 高效的事件管理机制
- **命令系统**: 完整的命令行界面支持
- **配置管理**: 灵活的配置文件系统，支持热重载
- **通知系统**: 实时消息通知
- **精美UI**：打滑了100个客户端的美丽视觉，给您带来最好的体验


## 🛠️ 安装指南

### 前置要求

1. 安装 Java 17 或更高版本
2. 安装 Minecraft Forge 1.20.1 (版本 47.3.0 或更高)

### 构建步骤

```bash
# 克隆项目
git clone https://github.com/ZiLin1337/Naven-Alpha2.git
cd Naven-Alpha2

# 构建项目
./gradlew build
```

构建完成后，模组文件将位于 `build/libs/` 目录中。

### 安装模组

1. 将生成的 `.jar` 文件复制到 Minecraft 的 `mods` 文件夹
2. 启动游戏即可使用

## ⚙️ 配置

### 基本设置

模组支持以下配置文件：

- `settings.json` - 主要配置文件
- `binds.json` - 按键绑定配置
- `friends.json` - 好友列表

### 快捷键

- **ClickGUI**: `右Shift` (默认)
- **模块切换**: 可在 ClickGUI 中自定义绑定

## 🎮 使用说明

### ClickGUI 使用

1. 按下 `右Shift` 打开 ClickGUI
2. 点击不同分类查看对应模块
3. 点击模块名称来启用/禁用功能
4. 点击设置图标配置模块参数

### 命令系统

模组内置命令前缀为 `.`，主要命令包括：

- `.bind <模块> <按键>` - 绑定快捷键
- `.config <操作>` - 配置管理
- `.language <语言>` - 切换语言

## 🔧 开发

### 项目结构

```
src/main/java/com/heypixel/heypixelmod/obsoverlay/
├── commands/          # 命令系统
├── events/           # 事件系统
├── files/            # 文件管理
├── modules/          # 功能模块
│   ├── impl/
│   │   ├── combat/   # 战斗模块
│   │   ├── misc/     # 杂项模块
│   │   ├── move/     # 移动模块
│   │   └── render/   # 渲染模块
├── ui/               # 用户界面
├── utils/            # 工具类
└── values/           # 配置值系统
```

### 添加新模块

1. 在对应分类目录下创建新的模块类
2. 继承 `Module` 类并添加 `@ModuleInfo` 注解
3. 实现所需的事件处理方法
4. 在 `ModuleManager` 中注册新模块

### API 使用

```java
// 创建新模块示例
@ModuleInfo(
    name = "MyModule",
    description = "模块描述",
    category = Category.MISC
)
public class MyModule extends Module {
    @EventTarget
    public void onUpdate(EventUpdate event) {
        // 实现功能逻辑
    }
}
```

## 📝 版本信息

- **当前版本**: Naven-Alpha2
- **Minecraft 版本**: 1.20.1
- **Forge 版本**: 47.4.6

## ⚖️ 许可证

本项目采用 All Rights Reserved 许可证。

## 🤝 贡献

欢迎提交 Issue 和 Pull Request 来改进项目。

## ⚠️ 免责声明

本模组仅供学习和研究目的使用。使用者需自行承担使用风险，开发者不对任何因使用本模组导致的问题负责。请遵守游戏服务器的规则和条款。

