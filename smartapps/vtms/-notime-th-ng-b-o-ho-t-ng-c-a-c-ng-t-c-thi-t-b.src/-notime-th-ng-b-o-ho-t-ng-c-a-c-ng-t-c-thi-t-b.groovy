definition(
    name: "[NoTime]Thông báo hoạt động của công tắc/thiết bị",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Giao tiếp",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences {
     
    section("Công tắc, thiết bị")
    {
    	input("swCC","capability.switch",title:"Chọn Công tắc, thiết bị", multiple:true, required:true)    
    }
    section("Nút thông báo")
    {
    	input("swNOI","capability.switch",title:"Nút thông báo")             
    }
    section ("Nhập nội dung thông báo")
    {
    	input name:"txt",type:"text", title:"Nhập nội dung",defaultValue:"  "
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
    subscribe(swNOI,"switch",sw_NOI)
}


def sw_CC(evt)

{
def val=swNOI.currentValue("switch")
if (val=="on")
{
  if(evt.value=="on") 	{sendPush( "${evt.displayName} đang mở/on. ${txt}" )}
  else if(evt.value=="off") 	{sendPush("${evt.displayName} đã tắt/off")}
 }
}