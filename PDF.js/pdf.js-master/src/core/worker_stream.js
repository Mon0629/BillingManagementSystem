/* Copyright 2019 Mozilla Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { assert } from "../shared/util.js";

/** @implements {IPDFStream} */
class PDFWorkerStream {
  constructor(msgHandler) {
    this._msgHandler = msgHandler;
    this._contentLength = null;
    this._fullRequestReader = null;
    this._rangeRequestReaders = [];
  }

  getFullReader() {
    assert(
      !this._fullRequestReader,
      "PDFWorkerStream.getFullReader can only be called once."
    );
    this._fullRequestReader = new PDFWorkerStreamReader(this._msgHandler);
    return this._fullRequestReader;
  }

  getRangeReader(begin, end) {
    const reader = new PDFWorkerStreamRangeReader(begin, end, this._msgHandler);
    this._rangeRequestReaders.push(reader);
    return reader;
  }

  cancelAllRequests(reason) {
    this._fullRequestReader?.cancel(reason);

    for (const reader of this._rangeRequestReaders.slice(0)) {
      reader.cancel(reason);
    }
  }
}

/** @implements {IPDFStreamReader} */
class PDFWorkerStreamReader {
  constructor(msgHandler) {
    this._msgHandler = msgHandler;
    this.onProgress = null;

    this._contentLength = null;
    this._isRangeSupported = false;
    this._isStreamingSupported = false;

    const readableStream = this._msgHandler.sendWithStream("GetReader");
    this._reader = readableStream.getReader();

    this._headersReady = this._msgHandler
      .sendWithPromise("ReaderHeadersReady")
      .then(data => {
        this._isStreamingSupported = data.isStreamingSupported;
        this._isRangeSupported = data.isRangeSupported;
        this._contentLength = data.contentLength;
      });
  }

  get headersReady() {
    return this._headersReady;
  }

  get contentLength() {
    return this._contentLength;
  }

  get isStreamingSupported() {
    return this._isStreamingSupported;
  }

  get isRangeSupported() {
    return this._isRangeSupported;
  }

  async read() {
    const { value, done } = await this._reader.read();
    if (done) {
      return { value: undefined, done: true };
    }
    // `value` is wrapped into Uint8Array, we need to
    // unwrap it to ArrayBuffer for further processing.
    return { value: value.buffer, done: false };
  }

  cancel(reason) {
    this._reader.cancel(reason);
  }
}

/** @implements {IPDFStreamRangeReader} */
class PDFWorkerStreamRangeReader {
  constructor(begin, end, msgHandler) {
    this._msgHandler = msgHandler;
    this.onProgress = null;

    const readableStream = this._msgHandler.sendWithStream("GetRangeReader", {
      begin,
      end,
    });
    this._reader = readableStream.getReader();
  }

  get isStreamingSupported() {
    return false;
  }

  async read() {
    const { value, done } = await this._reader.read();
    if (done) {
      return { value: undefined, done: true };
    }
    return { value: value.buffer, done: false };
  }

  cancel(reason) {
    this._reader.cancel(reason);
  }
}

export { PDFWorkerStream };
