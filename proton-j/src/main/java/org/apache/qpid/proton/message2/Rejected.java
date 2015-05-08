/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.qpid.proton.message2;

import java.util.List;

import org.apache.qpid.proton.codec2.DecodeException;
import org.apache.qpid.proton.codec2.DescribedTypeFactory;
import org.apache.qpid.proton.codec2.Encodable;
import org.apache.qpid.proton.codec2.Encoder;
import org.apache.qpid.proton.transport.DeliveryState;
import org.apache.qpid.proton.transport.ErrorCondition;

public final class Rejected implements DeliveryState, Outcome, Encodable
{
    public final static long DESCRIPTOR_LONG = 0x0000000000000025L;

    public final static String DESCRIPTOR_STRING = "amqp:rejected:list";

    public final static Factory FACTORY = new Factory();

    private ErrorCondition _error;

    public ErrorCondition getError()
    {
        return _error;
    }

    public void setError(ErrorCondition error)
    {
        _error = error;
    }

    @Override
    public void encode(Encoder encoder)
    {
        encoder.putDescriptor();
        encoder.putUlong(DESCRIPTOR_LONG);
        encoder.putList();
        if (_error != null)
        {
            _error.encode(encoder);
        }
        else
        {
            encoder.putNull();
        }
        encoder.end();
    }

    public static final class Factory implements DescribedTypeFactory
    {
        @SuppressWarnings("unchecked")
        public Object create(Object in) throws DecodeException
        {
            List<Object> l = (List<Object>) in;
            Rejected rejected = new Rejected();
            if (l.size() == 1)
            {
                rejected.setError((ErrorCondition) l.get(0));
            }
            return rejected;
        }
    }

    @Override
    public String toString()
    {
        return "Rejected{error=" + _error + "}";
    }
}