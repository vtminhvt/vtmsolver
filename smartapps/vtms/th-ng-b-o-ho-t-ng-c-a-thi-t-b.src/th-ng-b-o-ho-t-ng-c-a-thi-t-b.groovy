definition(
    name: "Thông báo hoạt động của thiết bị",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Giao tiếp",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences {
     
    section("Thiết bị, công tắt")
    {
    	input("swCC","capability.switch",title:"Chọn thiết bị, công tắt")       
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