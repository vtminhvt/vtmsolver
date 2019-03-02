definition(
    name: "[Hệ thống, Thông báo] Điều khiển gara, cổng",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển Gara, cửa cuốn",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
preferences 
{
    section("Chọn nút")
    {
    	input("swGR","capability.switch",title:"Nút bấm")             
    	input("swN","capability.switch",title:"Nút thông báo")             
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
      subscribe(swN,"switch",sw_N)
}

def sw_GR(evt)
{
def val=swN.currentValue("switch")
if (evt.value == "on")

	{
	   swGR.off()
       if (val=="on") sendPush("${txt}")
	}
}