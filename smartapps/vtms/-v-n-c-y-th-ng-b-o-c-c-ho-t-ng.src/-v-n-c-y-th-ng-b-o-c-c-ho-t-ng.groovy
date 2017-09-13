definition(
    name: "[Vườn cây]Thông báo các hoạt động",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Trình điều khiển, giao tiếp, ứng dụng cơ bản",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences {
     
    section("Công tắc điện")
    {
    	input("swCC","capability.switch",title:"Máy phun sương")       
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
 if(evt.value=="on") 	{thongbao("Máy phun sương đang hoạt động")}
 else if(evt.value=="off") 	{thongbao("Máy phun sương đã tắt")}
}