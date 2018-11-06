definition(
    name: "[Vườn cây]Thông báo các hoạt động",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Trình điều khiển, giao tiếp, ứng dụng cơ bản",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences {
     
    section("Công tắc điện")
    {
    	input("swCC","capability.switch",title:"Máy phun sương",multiple:true, required:true)
    }  
}
def installed() 
{
	init()
}
def updated() 
{
	init()	
}
def init()
{ 
    subscribe(swCC,"switch",sw_CC)
}

def thongbao(msg)
{
	sendPush(msg)
}
def tb(msg)
{
	sendPush(msg)
}

def sw_CC(evt)
{
 if(evt.value=="on") 	{thongbao( "${evt.displayName} đang hoạt động")}
 else if(evt.value=="off") 	{thongbao("${evt.displayName} đã tắt")}
}