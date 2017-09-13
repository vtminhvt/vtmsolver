definition(
    name: "[Gara]Duy trì nút nhấn ảo",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển Gara, cửa cuốn",
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
}

def sw_GR(evt)
{
if (evt.value == "on")
	{
        swGR.off()
	}
}