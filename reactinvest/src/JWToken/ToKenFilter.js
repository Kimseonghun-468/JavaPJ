import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';

function TokenValidation() {
    const [isValid, setIsValid] = useState(null);
    const [userInfo, setUserInfo] = useState({});
    useEffect(() => {
        // localStorage 또는 sessionStorage에서 JWT 가져오기
        const token = localStorage.getItem('Authorization'); // JWT 저장 위치에 따라 변경

        if (token) {
            // 서버에 유효성 검증 요청
            fetch('http://localhost:8080/token-Validation', {
                method: 'POST',
                headers: {
                    'Authorization': `${token}` // 헤더에 JWT 토큰 포함
                }
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    if(data.authorities){
                        setUserInfo(data);
                        setIsValid(true);
                    }
                    else{
                        setIsValid(false);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    setIsValid(false);

                });
        } else {
            setIsValid(false);
        }
    }, []);

    if (isValid === null) {
        return <div>토큰을 검증 중 입니다...</div>;
    }

    return isValid ? userInfo : null;
}
function PrivateRoute({ children }) {
    const token = TokenValidation()
    if (!token) {
        return <Navigate to="/Member/login" />;
    }
    return children(token.name)
}

export default PrivateRoute;