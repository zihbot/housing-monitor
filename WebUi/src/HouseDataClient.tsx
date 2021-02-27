import { useEffect, useState } from "react";
import { HouseElement, KeyValuePair } from "./model";

export interface HouseProperties {
    data: KeyValuePair[];
}

export interface HouseElementsList {
    data: HouseElement[];
}

export const useGetHouseProperties = (id: bigint) => {
    const [result, setResult] = useState<HouseProperties>({ data: [] });

    useEffect(() => {
        fetch('http://127.0.0.1:3000/rest/properties/' + id)
            .then(r => r.json())
            .then(r => setResult({ data: r }) );
    }, []);

    return result;
};

export const useGetHousesList = () => {
    const [result, setResult] = useState<HouseElementsList>({ data: [] });

    useEffect(() => {
        fetch('http://127.0.0.1:3000/rest/house/')
            .then(r => r.json())
            .then(r => setResult({ data: r }) );
    }, []);

    return result;
};