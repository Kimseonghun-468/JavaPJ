import React from 'react';
// import './Goodbye.css';

function Goodbye() {
    // 내부에서 사용할 함수 1
    function handleClick() {
        alert('Goodbye!');
    }

    // 내부에서 사용할 함수 2
    function renderMessage() {
        return <p>Thank you for visiting! We hope to see you again soon.</p>;
    }

    // 컴포넌트의 렌더링 부분
    return (
        <div className="goodbye-container">
            <h1>Goodbye</h1>
            {renderMessage()}  {/* 함수 호출로 메시지 렌더링 */}
            <button onClick={handleClick}>Click Me</button>  {/* 클릭 시 함수 호출 */}
        </div>
    );
}

export default Goodbye;