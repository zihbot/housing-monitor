import React from 'react';
import { useGetHousesList } from './HouseDataClient';

type Props = {
    onSelectHouse: (id: bigint) => void
}

export const HousesList: React.FC<Props> = ({onSelectHouse}) => {
    const client = useGetHousesList();

    return (<div>
            <ul>
            { client.data.map(kv => 
                <li key={kv.id.toString()}>
                    <a onClick={() => onSelectHouse(kv.id)}>{kv.id}: {kv.url}</a>
                </li>
            ) }
            </ul>
        </div>
    );
}