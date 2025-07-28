import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  define: {
    global: 'window',  // 'global'을 'window'로 정의
  },
  server: {
    proxy: {
      '/ws': {
        target: 'http://192.168.0.117:8080', // 실제 웹소켓 서버 주소
        changeOrigin: true,
        ws: true, // 웹소켓 프로토콜을 사용하도록 설정
      },
    },
    host: '0.0.0.0', // 모든 네트워크 인터페이스에서 접근 가능하도록 설정
    port: 5137,      // 원하는 포트 번호로 설정
  },
})
