import React from 'react';
import { useParams } from 'react-router-dom';

function Portfolio(){
    const { username } = useParams();
    return (
        <div>
            <h1>{username}'s Portfolio</h1>
        </div>
    )
}
export default Portfolio