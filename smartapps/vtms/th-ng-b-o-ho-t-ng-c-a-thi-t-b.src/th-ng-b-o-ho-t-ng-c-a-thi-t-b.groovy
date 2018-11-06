definition(
    name: "Thông báo hoạt động của thiết bị",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Giao tiếp",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences {
     
    section("Thiết bị, công tắt")
    {
    	input("swCC","capability.switch",title:"Chọn thiết bị, công tắt", multiple:true, required:true)    
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
 if(evt.value=="on") 	{thongbao( "${evt.displayName} đang mở/on")}
 else if(evt.value=="off") 	{thongbao("${evt.displayName} đã tắt/off")}
}