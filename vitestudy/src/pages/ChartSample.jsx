import React, { useEffect, useState } from 'react';
import { Chart, ChartCanvas, XAxis, YAxis, CandlestickSeries, HoverTooltip, CrossHairCursor, ZoomButtons } from 'react-financial-charts';
import { scaleTime } from 'd3-scale';
import {useParams} from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

function FinancialChart() {
    const { id } = useParams();
    const [price, setPrice] = useState(null);


    useEffect(() => {
        const socket = new SockJS('http://192.168.0.117:8080/ws');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            stompClient.subscribe(`/topic/Chart/XRP` , (message) => {
                console.log(message.body)
                setPrice(JSON.parse(message.body));
            });
        }, (error) => {
            console.error('WebSocket 연결 실패:', error);
        });

        return () => {
          if (stompClient && stompClient.connected) {
            stompClient.disconnect();
          }
        };
    }, [id]);


    // 샘플 데이터
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true); // 로딩 상태 관리
    useEffect(() => {
      // POST 요청을 보내기 위한 fetch 호출
      fetch('http://192.168.0.117:8080/api/selectCandle', {
        method: 'POST',  // POST 메서드 사용
        headers: {
          'Content-Type': 'application/json', // 요청 본문이 JSON임을 명시
        },
        body: JSON.stringify({
          // 여기에서 요청 본문에 필요한 데이터를 추가
          symbol: 'BTCUSDT',
          timeFrame: '1m',
        }),
      })
        .then((response) => response.json()) // 응답을 JSON으로 변환
        .then((data) => {
            const transformedData = data.map(item => ({
              date: new Date(item.candle_date_time_kst),  // KST 시간으로 변환된 날짜 객체
              open: item.opening_price,                    // opening_price
              high: item.high_price,                      // high_price
              low: item.low_price,                        // low_price
              close: item.trade_price,                    // trade_price
            }));

          setData(transformedData); // 상태 업데이트
            setLoading(false); // 로딩 완료
        })
        .catch((error) => {
          console.error('API 요청 중 오류 발생:', error);
            setLoading(false); // 로딩 완료
        });
    }, []); // 빈 배열을 넣어 최초 한 번만 요청하게 함


    useEffect(() => {
       if (price) {
           // 새로운 날짜 객체 생성, 초와 밀리초를 00으로 설정
           const newDate = new Date();
           newDate.setSeconds(0, 0); // 초와 밀리초를 00으로 설정

           // 같은 날짜가 있는지 확인
           const updatedData = data.map(item =>
               item.date.getTime() === newDate.getTime()  // 시간 비교
                   ? { ...item,
                       open: price.opening_price,
                       high: price.high_price,
                       low: price.low_price,
                       close: price.trade_price
                     }  // 같은 시간일 경우 덮어쓰기
                   : item
           );

           // 새로운 시간의 데이터가 없으면 새 데이터 추가
           if (!updatedData.some(item => item.date.getTime() === newDate.getTime())) {
               updatedData.push({
                   date: newDate,
                   open: price.opening_price,
                   high: price.high_price,
                   low: price.low_price,
                   close: price.trade_price,
               });
           }

           setData(updatedData); // 상태 업데이트
       }
   }, [price]); // price가 바뀔 때마다 실행

    if (loading) {
        return <div>Loading...</div>; // 로딩 중일 때 표시할 내용
    }

    return (
        <ChartCanvas
            width={800}
            height={400}
            ratio={3}
            margin={{ left: 50, right: 50, top: 10, bottom: 30 }}
            data={data}
            xAccessor={(d) => d.date}
            xScale={scaleTime()}
        >
            <Chart id={0} yExtents={d => [d.high, d.low]}>
                <XAxis />
                <YAxis />
                <CandlestickSeries />
                <HoverTooltip />
                <CrossHairCursor />
                <ZoomButtons />
            </Chart>
        </ChartCanvas>
    );

    // return (
    //     <h1>data</h1>
    // )
}

export default FinancialChart;
