local toast = luajava.bindClass("android.widget.Toast"):makeText(context(),"I'm from lua script!",1)
toast:show()

-- 注释 startActivity
local intent = luajava.newInstance("android.content.Intent")
-- targetSdk = 22时必须加此NEW_TASK
intent:addFlags(0x10000000)
intent:setClassName("com.android.settings", "com.android.settings.Settings")

local context = context()
context:startActivity(intent)
eval("跳转成功")