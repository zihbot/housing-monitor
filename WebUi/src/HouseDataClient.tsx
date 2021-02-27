import { useCallback, useEffect, useState } from 'react';

type GetClient<T> = {
    result: {data: T | undefined},
    getRequest: () => void
}

export const useRestGetClient = <T extends unknown>(uri: string): GetClient<T> => {
    const [result, setResult] = useState<{data: T | undefined}>({ data: undefined });

    const getRequest = useCallback(() => {
        fetch('http://127.0.0.1:3000/rest/' + uri)
            .then(r => r.json())
            .then(r => setResult({ data: r }) );
    }, []);

    useEffect(getRequest, []);

    return {result, getRequest};
};

type PostClient<T> = {
    result: {data: T | undefined},
    postRequest: (postData: object) => void
}

export const useRestPostClient = <T extends unknown>(
        uri: string, 
        finishCallback: () => void = () => {}): PostClient<T> => {
    const [result, setResult] = useState<{data: T | undefined}>({ data: undefined });

    const postRequest = useCallback((postData) => {
        fetch('http://127.0.0.1:3000/rest/' + uri, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postData)
        })
            .then(r => r.json())
            .then(r => { setResult({ data: r }); 
                finishCallback()});
    }, []);

    return {result, postRequest};
};
