definition(
    name: "[Hệ thống] Điều khiển gara, cổng",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển Gara, cửa cuốn",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
preferences 
{
    section("Chọn nút bấm bị tác động")
    {
    	input("swGR","capability.switch",title:"Nút bấm")             
    }
    
    
        section("Nội dung thông báo")
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
}

def sw_GR(evt)
{
if (evt.value == "on")
	{
	   swGR.off()
       sendPush("${txt}")
	}
}