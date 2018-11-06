definition(
    name: "Thông báo trạng thái khi rời khỏi nhà",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
//test: OK

preferences 
{
	section("Kích hoạt kịch bản")
	{
		input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
	}
    
    section("Chọn các cảm biến đóng mở cần cảnh báo")
    {
    	input("csCC","capability.contactSensor",title:"Cảm biến Cửa chính")
    	input("csT2","capability.contactSensor",title:"Cảm biến Tầng 2")
    	input("csT3","capability.contactSensor",title:"Cảm biến Tầng 3")
    	input("csT4","capability.contactSensor",title:"Cảm biến Tầng 4")
        input("csT4s","capability.contactSensor",title:"Cảm biến Tầng 4 sau")
    }
    section("Cảm biến hiện diện")
    {
    	input("presenceNguyen","capability.presenceSensor",title:"Cảm biến hiện diện Nguyên")
       	input("presenceNguyeniPhone","capability.presenceSensor",title:"iPhone của Nguyen")
    }
    
}
def init()
{
    subscribe(csCC,"contact",cs_CC)
    subscribe(csT2,"contact",cs_T2)
    subscribe(csT3,"contact",cs_T3)
    subscribe(csT4,"contact",cs_T4)
    subscribe(csT4s,"contact",cs_T4s)
    
    subscribe(presenceNguyen,"presence",presence_Nguyen)
    subscribe(presenceNguyeniPhone,"presence",presence_NguyeniPhone)
}
def installed() 
{
    init()	
}
def updated() 
{
	init()	
	unschedule()	
}
def presence_Nguyen(evt)
{
    if (sel=="on" && evt.value!="present")
    {
        def cs1=(csCC.currentValue("contact")=="closed")
        def cs2=(csT2.currentValue("contact")=="closed")
        def cs3=(csT3.currentValue("contact")=="closed")
        def cs4=(csT4.currentValue("contact")=="closed")
        def cs4s=(csT4s.currentValue("contact")=="closed")   
       
       if (! (cs1 && cs2 && cs3 && cs4 && cs4s))
        {
            sendPush("[Present]Bạn đã rời khỏi nhà, có vài cửa vẫn đang mở \n Trạng thái các cửa là: \n Cửa T1:${csCC.currentValue("contact")} \n Cửa T2:${sT2.currentValue("contact")} \n Cửa T3:${csT3.currentValue("contact")} \n Cửa T4:${csT4.currentValue("contact")} \n Cửa T4s:${csT4s.currentValue("contact")} ")
        }
        else
        {
            sendPush("[Present]Trạng thái: Nhà của bạn đã an toàn!")
        }
    }
}

def presence_NguyeniPhone(evt)
{
    if (sel=="on" && evt.value!="present")
    {
        def cs1=(csCC.currentValue("contact")=="closed")
        def cs2=(csT2.currentValue("contact")=="closed")
        def cs3=(csT3.currentValue("contact")=="closed")
        def cs4=(csT4.currentValue("contact")=="closed")
        def cs4s=(csT4s.currentValue("contact")=="closed")   
       
       if (! (cs1 && cs2 && cs3 && cs4 && cs4s))
        {
            sendPush("[iPhone]Bạn đã rời khỏi nhà, có vài cửa vẫn đang mở \n Trạng thái các cửa là: \n Cửa T1:${csCC.currentValue("contact")} \n Cửa T2:${sT2.currentValue("contact")} \n Cửa T3:${csT3.currentValue("contact")} \n Cửa T4:${csT4.currentValue("contact")} \n Cửa T4s:${csT4s.currentValue("contact")} ")
        }
        else
        {
            sendPush("[iPhone]Bạn đã ra khỏi nhà. Trạng thái: Nhà của bạn đã an toàn!")
        }
    }
}