import React from 'react';
import useGetHouseData from './HouseDataClient';

export const HouseData: React.FC<{}> = () => {
    const client = useGetHouseData();

    return (<div>
            <table>
            { client.data.map(kv => 
                <tr>
                    <td>{kv.key}</td>
                    <td>{kv.value}</td>
                </tr>
            ) }
            </table>
        </div>
    );
}