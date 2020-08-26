import com.vdurmont.semver4j.Semver
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.description.PluginKind
import net.mamoe.mirai.console.plugin.jvm.JarPluginLoader
import net.mamoe.mirai.console.plugin.jvm.JvmMemoryPluginDescription
import net.mamoe.mirai.console.pure.MiraiConsolePureLoader
import org.example.myplugin.MyPluginMain

fun main() {
    MiraiConsolePureLoader.main(arrayOf())

    // 如下启动方案预计在 1.0-RC 支持

    val description = JvmMemoryPluginDescription(
        kind = PluginKind.NORMAL,
        name = "ExamplePlugin",
        author = "Author",
        version = Semver("1.0.0"), // for test
        info = "An example plugin",
        dependencies = listOf(),
    )
    JarPluginLoader.load(description) // 模拟 "plugin.yml" 加载插件

    MyPluginMain.enable() // 主动启用插件
}