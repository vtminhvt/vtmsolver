definition(
    name: "[Gara]Kiểm tra vật cản trước khi điều khiển cửa cuốn",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển Gara, cửa cuốn",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
preferences 
{
 
section ("Thời gian tác động lên công tắc")
    {
     input name: "timeofP", type: "number", title: "Tác động mở trong bao nhiêu giây?", defaultValue:"1"
    }

    section("Chọn công tắc kiểm tra vật cản")
    {
    	input("sw1","capability.switch",title:"Kiểm tra vật cản")             
    	input("sw2","capability.switch",title:"Nút điều khiển cửa cuốn đi xuống")
		input("sw3","capability.switch",title:"Nút ảo điều khiển cửa cuốn đi xuống")                          
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
    subscribe(sw1,"switch",sw_1)
    subscribe(sw2,"switch",sw_2)
	subscribe(sw3,"switch",sw_3)
}

def sw_3(evt)
{

def p1= timeofP*1000

def valsw1 = sw1.currentValue("switch")

if (evt.value == "on")
{
    if (valsw1=="on")
    {
   		sendPush("${txt}")
    }
    else
    {
        sw2.on() 
    }
}

  sw3.off()

}