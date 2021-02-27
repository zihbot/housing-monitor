import React from 'react';
import { useGetHouseProperties } from './HouseDataClient';

type Props = {
    id: bigint,
    onBack: () => void
}

export const HouseData: React.FC<Props> = ({id, onBack}) => {
    const client = useGetHouseProperties(id);

    return (<div>
            <table>
                <tbody>
                { client.data.map(kv => 
                    <tr key={kv.key}>
                        <td>{kv.key}</td>
                        <td>{kv.value}</td>
                    </tr>
                ) }
                </tbody>
            </table>
            <a onClick={onBack}>Vissza</a>
        </div>
    );
}