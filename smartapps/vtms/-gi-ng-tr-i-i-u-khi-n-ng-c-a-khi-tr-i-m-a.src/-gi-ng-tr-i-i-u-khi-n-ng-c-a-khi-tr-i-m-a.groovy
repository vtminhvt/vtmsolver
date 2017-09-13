definition(
    name: "[Giếng trời]Điều khiển đóng cửa khi trời mưa",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển giếng trời",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
preferences 
{
 
section ("Thời gian tác động lên công tắc")
	{
     input name: "timeofP", type: "number", title: "Tác động mở trong bao nhiêu giây?", defaultValue:"1"
    }

    section("Chọn công tắc điện bị tác động")
    {
    	input("swGR","capability.switch",title:"Công tắc điện")             
    }
    
     section("Chọn cảm biến trạng thái cửa")
    {
        input("csGR","capability.contactSensor",title:"Cảm biến đóng, mở")
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
}

def sw_GR(evt)
{

def p1= timeofP*1000

def chk=csGR.currentValue("contact")=="open"

if (evt.value == "on" && chk  )
	{
	sendPush("${txt}")  
	}
 swGR.off()
}