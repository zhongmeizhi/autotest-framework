框架使用指南：
	excel后缀名为 xlsx(可以更改代码)，excel的第一行不会被代码读取（可以写备注）
	失败后的截图文件在 \ZhuYuan\test-output\snapshot 目录下
	在文件路径 /ZhuYuan/test-output/index.html 的 "Reporter output"中查看自制测试报告
1、在 properties.xlsx 中填写：excel表路径  	excel名称	sheet名称
	路径是 ./xxx/ 要以 "/"结尾
	可以同时运行多个 excel 表的用例（更改代码可以并发）
2、将用例步骤填写到 其他excel表中
	excel的1,2,3,4 列 分别是：操作，控件，参数，说明，如果没有说明，在"Reporter output"显示 empty
	纯数字参数要用 [] 中括号包裹 
	控件获得方法：FireFox浏览器，下载 FireBug插件和FirePath插件，打开F12 然后选择元素获得 xpath 路径
3、运行 ZY.java 程序

目前有的方法 （方法名大小写都可以）
（控件值：元素的 xpath 定位器）
打开网址：		    open 		 + 参数（http://xxxx） 		
点击 ：		     click	+ 控件
文本输入:		    sendkeys   + 控件	+ 参数（文本）					
上传文件	:非 flash 上传可以用上面的sendkeys方法,参数为要上传的文件:路径+名称 例如 C:\Users\123\Pictures\a.png
按键盘ENTER键 :	    enter											
鼠标悬停在元素上：	hover	  + 控件					用于：鼠标悬停在元素上其他元素才出现，
屏幕移动到元素 ：	 move	   + 控件					 用于：(一般出现在谷歌浏览器)解决不能点击不在显示区域的元素
删除标签和内容：	 delete	   + 控件				 	 用于：被X标签遮挡需要的元素，那么把X标签删除，例：position:fixed;
选中选择框value :	   select     + 控件     + 参数(option的value值)	用于：下拉框，选取下拉框的值
切换屏幕：		   switch				             用于：Auto运行中打开新浏览器窗口，需要witch窗口，(如果窗口超过2个，这方法需要更改)
进入IFrame :	     iframe	+ 控件				     用于：元素在<iframe>或<frame>中要先进入IFrame才能操作元素
等待 ：				wait 			 + 参数（时间）单位为:秒			用于：中途暂停，等待页面
等待元素出现 :		waitele	 + 控件	 + 参数（时间）单位为:秒			用于：等待未加载完成的元素(比wait智能，只有元素出现就会停止等待)
接受警告：			accept											用于：接受弹出的Alert警告框
不接受警告：			unaccept										用于：取消弹出的confirm框
结束且页面不关闭：	end	
富文本框输入：		sendarea										doing

//下面的是断言用的方法: 
获取文本给参数：		text	 + 控件	 + 参数(自定义名称，例如：A),		 意思是将元素 的文本赋值给 A
获取元素属性：		attr	 + 控件	 + 参数(自定义名称)=(属性名称:例如:id,class,style,name), "B=id"的意思是将 id 的属性值给 B
断言A等于B:			assert	 		 + A=B							用于:断言(自定义名称的参数)A=(自定义名称的参数)B
断言元素文本等于参数:textis 	 + 控件	 + 参数(文本)					用于：判断元素文本内容
断言标题等于参数：	titleis  		 + 参数(标题)					用于：判断窗口页面标题


需要增加的方法：
断言失败：			no
图片断言：			imgis
数量断言：			numis


需要改善
1、增加并发，
2、打包运行
3、浏览器多样
4、自主性优化
5、屏幕截图
6、容错性优化
7、一键集成 
8、前端勾选运行

