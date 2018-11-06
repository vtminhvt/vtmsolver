definition(
    name: "[Giếng trời]Điều khiển đóng cửa khi trời mưa",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển giếng trời",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
preferences 
{
 
section ("Thời gian tác động lên công tắc")
	section("Chọn công tắc điện bị tác động")
    {
    	input("swGR","capability.switch",title:"Tên công tắc")             
    }
    
     section("Chọn cảm biến trạng thái cửa")
    {
        input("csGR","capability.contactSensor",title:"Tên cảm biến")
    }
     section("Chọn cảm biến phát hiện nước")
    {
        input("wtRain","capability.waterSensor",title:"Tên cảm biến")
    }
    
    section("Nội dung hoạt động")
    	{
        	input name:"txt",type:"text", title:"Nhập nội dung",defaultValue:" "
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
   
   subscribe(swGR,"switch",sw_GR)
   subscribe(csGR,"contact",cs_GR)
   subscribe(wtRain,"water",wt_Rain)
}

def wt_Rain(evt)
{
	def chk=csGR.currentValue("contact")=="open"
	if (evt.value=="wet" && chk) 
    {
    	swGR.on()
    }
}

def sw_GR(evt)
{
def chk=csGR.currentValue("contact")=="open"
if (evt.value == "on" && chk  )
	{
	sendPush("${txt}")  
	}
swGR.off()
}