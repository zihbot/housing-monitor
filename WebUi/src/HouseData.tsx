import React from 'react';
import { useRestGetClient } from './HouseDataClient';
import { KeyValuePair } from './model';

type Props = {
    id: bigint,
    onBack: () => void
}

const HouseData: React.FC<Props> = ({id, onBack}) => {
    const client = useRestGetClient<KeyValuePair[]>('properties/' + id);

    return (<div>
            <table>
                <tbody>
                { client.result.data && client.result.data.map(kv => 
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

export default HouseData