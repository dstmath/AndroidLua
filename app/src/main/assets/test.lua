local toast = luajava.bindClass("android.widget.Toast"):makeText(context(),"I'm from lua script!",1)
toast:show()

-- 注释 startActivity
local intent = luajava.newInstance("android.content.Intent")
intent:setClassName("com.android.settings", "com.android.settings.Settings")

local context = context()
context:startActivity(intent)