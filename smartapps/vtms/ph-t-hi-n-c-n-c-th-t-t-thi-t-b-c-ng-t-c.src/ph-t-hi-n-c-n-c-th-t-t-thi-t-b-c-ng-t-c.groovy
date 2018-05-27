definition(
    name: "Phát hiện có nước thì tắt thiết bị, công tắc",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
preferences 
{
 
section ("Thời gian tác động lên công tắc")
	section("Chọn công thiết bị, tắc điện bị tác động")
    {
    	input("swGR","capability.switch",title:"Tên công tắc")             
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
   subscribe(wtRain,"water",wt_Rain)
}

def wt_Rain(evt)
{
	if (evt.value=="wet") 
    {
    	swGR.off()
    }
}