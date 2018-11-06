definition(
    name: "[Gara]Mô phỏng nút ấn",
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
    
    section("Nút thông báo")
    {
    	input("swNOI","capability.switch",title:"Nút thông báo")             
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
   subscribe(swNOI,"switch",sw_NOI)
    subscribe(swGR,"switch",sw_GR)
}
def sw_GR(evt)
{
def val=swNOI.currentValue("switch")
if (evt.value == "on")

	{
	 if  (val=="on") sendPush("${txt}")
       swGR.off()
	}
}