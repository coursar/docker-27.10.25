const rootEl = document.getElementById('root');

const password = 'secret';
const encoder = new TextEncoder()
const passwordMaterial = encoder.encode(password);
const pbkdf2Key = await crypto.subtle.importKey(
    'raw',
    passwordMaterial,
    'PBKDF2',
    false,
    ['deriveKey']
);
const salt = Uint8Array.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
const iterations = 1000;
const derivedKey = await crypto.subtle.deriveKey(
    {
        name: 'PBKDF2',
        hash: 'SHA-256',
        salt,
        iterations,
    },
    pbkdf2Key,
    {
        name: 'AES-GCM',
        length: 256,
    },
    true,
    ['encrypt', 'decrypt'],
);
const exportedKey = await crypto.subtle.exportKey('raw', derivedKey);

rootEl.textContent = new Uint8Array(exportedKey).toHex(); 
