# Naven-Alpha (Fabric 1.20.6)

一个基于Naven-Alpha开源的修改客户端，已从Forge 1.20.1迁移到Fabric 1.20.6。

原项目开源地址： https://github.com/jiuxianqwq/NavenAlpha

## 🚀 特性

### 核心功能

- **Fabric 1.20.6 支持**: 完全适配最新的 Fabric 环境
- **模块化架构**: 70+个功能模块，支持动态加载和管理
- **事件驱动系统**: 高效的自定义事件管理机制
- **GPU加速UI**: 使用 Skija 进行高性能 UI 渲染
- **命令系统**: 完整的命令行界面支持
- **配置管理**: 灵活的配置文件系统，支持热重载
- **通知系统**: 实时消息通知

## 🛠️ 安装指南

### 前置要求

1. 安装 Java 17 或更高版本
2. 安装 Fabric Loader 0.15.11 或更高版本
3. 安装 Fabric API 0.98.0+

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

1. 将生成的 `naven-alpha-1337.jar` 文件复制到 Minecraft 的 `mods` 文件夹
2. 确保已放入 Fabric API
3. 启动游戏即可使用

## ⚙️ 配置

### 基本设置

模组支持以下配置文件 (位于 `.minecraft/naven/`):

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
├── ui/               # 用户界面
├── utils/            # 工具类
└── values/           # 配置值系统
```

### 迁移说明 (Forge -> Fabric)

- 构建系统已迁移至 Fabric Loom
- 事件系统已适配 Fabric
- Mixins 已更新至 1.20.6 Yarn 映射
- 核心库 (SmartBoot AIO, Skija) 已完成适配

## 📝 版本信息

- **当前版本**: 1337
- **Minecraft 版本**: 1.20.6
- **Loader 版本**: Fabric Loader 0.15.11+
- **Fabric API**: 0.98.0+

## ⚖️ 许可证

本项目采用 All Rights Reserved 许可证。

## 🤝 贡献

欢迎提交 Issue 和 Pull Request 来改进项目。

## ⚠️ 免责声明

本模组仅供学习和研究目的使用。使用者需自行承担使用风险，开发者不对任何因使用本模组导致的问题负责。请遵守游戏服务器的规则和条款。
