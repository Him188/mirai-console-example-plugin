package org.example.myplugin

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.jvm.loadSetting
import net.mamoe.mirai.console.setting.Setting
import net.mamoe.mirai.console.setting.getValue
import net.mamoe.mirai.console.setting.setValue
import net.mamoe.mirai.console.setting.value
import net.mamoe.mirai.console.util.ConsoleExperimentalAPI
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.utils.info

object MyPluginMain : KotlinPlugin() {

    // 这个 PluginCommandOwner 可能会在 1.0-M3 修改.
    object MyCommandOwner : PluginCommandOwner(MyPluginMain)

    override fun onEnable() {
        file("test.txt").writeText("123") // 在 data/插件名/ 创建 "text.txt" 文件, 写入内容 "Hello"

        logger.info { "Hi: ${MySetting.name}" } // 输出一条日志.
        logger.info("Hi: ${MySetting.name}") // 输出一条日志. 与上面一条相同, 但更推荐上面一条.
        logger.verbose("Hi: ${MySetting.name}") // 多种日志级别可选

        // 请不要使用 println, System.out.println 等标注输出方式. 请总是使用 logger.

        MySetting.count++ // 对 Setting 的改动会自动在合适的时间保存

        MySimpleCommand.register() // 注册指令
    }

    override fun onDisable() {
        MySimpleCommand.unregister() // 取消注册指令
    }
}

// 定义一个配置. 所有属性都会被追踪修改, 并自动保存.
// 配置是插件与用户交互的接口, 但不能用来保存插件的数据.
object MySetting : Setting by MyPluginMain.loadSetting() {
    val name by value("test")

    var count by value(0)
}

// 插件的数据. console 不提供
@Serializable
data class MyData(
    val list: List<String>
)

// 简单指令
object MySimpleCommand : SimpleCommand(
    MyPluginMain.MyCommandOwner, "foo",
    description = "示例指令"
) {
    // 通过 /foo 调用, 参数自动解析

    @Handler
    suspend fun CommandSender.handle(int: Int, str: String) { // 函数名随意, 但参数需要按顺序放置.
        sendMessage("/foo 的第一个参数是 $int, 第二个是 $str")
    }
}

// 复合指令
@OptIn(ConsoleExperimentalAPI::class)
object MyCompositeCommand : CompositeCommand(
    MyPluginMain.MyCommandOwner, "manage",
    description = "示例指令", permission = MyCustomPermission,
    // prefixOptional = true // 还有更多参数可填, 此处忽略
) {

    // [参数智能解析]
    //
    // 在控制台执行 "/manage <群号>.<群员> <持续时间>",
    // 或在聊天群内发送 "/manage <@一个群员> <持续时间>",
    // 或在聊天群内发送 "/manage <目标群员的群名> <持续时间>",
    // 或在聊天群内发送 "/manage <目标群员的账号> <持续时间>"
    // 时调用这个函数
    @SubCommand
    suspend fun CommandSender.mute(target: Member, duration: Int) { // 通过 /manage mute <target> <duration> 调用
        sendMessage("/manage mute 被调用了, 参数为: $target, $duration")

        val result = kotlin.runCatching {
            target.mute(duration).toString()
        }.getOrElse {
            it.stackTraceToString()
        } // 失败时返回堆栈信息

        sendMessage("结果: $result")
    }

    @SubCommand
    suspend fun CommandSender.list() { // 执行 "/manage list" 时调用这个函数
        sendMessage("/manage list 被调用了")
    }

    // 支持 Image 类型, 需在聊天中执行此指令.
    @SubCommand
    suspend fun CommandSender.test(image: Image) { // 执行 "/manage test <一张图片>" 时调用这个函数
        sendMessage("/manage image 被调用了, 图片是 ${image.imageId}")
    }
}


// 定义自定义指令权限判断
object MyCustomPermission : CommandPermission {
    override fun CommandSender.hasPermission(): Boolean {
        // 高自由度的权限判定

        /*
        return if (this is FriendCommandSender) {
            this.user.id == 123456L
        } else false
        */

        return true
    }
}